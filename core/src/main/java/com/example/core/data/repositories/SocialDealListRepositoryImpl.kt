package com.example.core.data.repositories

import com.example.core.data.CustomResult
import com.example.core.data.convertToUSD
import com.example.core.data.local.dataStore.DataStoreManager
import com.example.core.data.local.db.SocialDealsDao
import com.example.core.data.remote.ApiService
import com.example.core.data.toSocialDeal
import com.example.core.data.toSocialDealEntity
import com.example.core.data.toStringWithTwoDecimals
import com.example.core.domain.model.SocialDeal
import com.example.core.domain.repositories.SocialDealListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class SocialDealListRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val socialDealsDao: SocialDealsDao,
    private val dataStoreManager: DataStoreManager
) : SocialDealListRepository {
    override fun getSocialDealList(): Flow<CustomResult<List<SocialDeal>>> = flow {
        // First emit current local DB data
        val localData =
            socialDealsDao.getSocialDealList().map { list -> list.map { it.toSocialDeal() } }
                .first()

        if (localData.isNotEmpty()) {
            Timber.d("Source: Local")
            emit(CustomResult.Success(localData))
        } else {
            // If no data locally, fetch from API and update DB
            try {
                val response = apiService.getSocialDealsList()
                val deals = response.deals.map { it.toSocialDeal() }
                socialDealsDao.upsertSocialDeals(deals.map { it.toSocialDealEntity() }) // Convert to entity
                Timber.d("Source: From Remote")
                emit(CustomResult.Success(deals))
            } catch (e: Exception) {
                emit(CustomResult.Failure(e.localizedMessage ?: "Something went wrong"))
            }
        }

        // Then keep emitting DB changes after that (reactive updates)
        emitAll(
            combine(
                socialDealsDao.getSocialDealList().map { list -> list.map { it.toSocialDeal() } },
                dataStoreManager.getUserCurrency().map { result ->
                    // Assuming CustomResult.Success or fallback to "EUR"
                    when (result) {
                        is CustomResult.Success -> result.data
                        else -> "EUR"
                    }
                }
            ) { deals, currency ->

                // Transform deals prices depending on currency
                val transformedDeals = deals.map { deal ->
                    val newPriceFloat = deal.newPrice.toFloatOrNull() ?: 0f
                    val oldPriceFloat = deal.oldPrice.toFloatOrNull() ?: 0f

                    when (currency) {
                        "USD" -> deal.copy(
                            newPrice = convertToUSD(newPriceFloat).toStringWithTwoDecimals(),
                            oldPrice = convertToUSD(oldPriceFloat).toStringWithTwoDecimals(),
                            currencySign = "$"
                        )

                        // Because deals are saved with EUR by default.
                        // No need to convert in this case.
                        "EUR" -> deal.copy(
                            newPrice = newPriceFloat.toStringWithTwoDecimals(),
                            oldPrice = oldPriceFloat.toStringWithTwoDecimals(),
                            currencySign = "€"
                        )

                        else -> deal
                    }
                }

                CustomResult.Success(transformedDeals)
            }
        )
    }.catch { e ->
        emit(CustomResult.Failure(e.localizedMessage ?: "Error"))
    }

    override suspend fun setFavoriteSocialDeal(socialDeal: SocialDeal) {
        socialDealsDao.updateSocialDeal(socialDeal = socialDeal.toSocialDealEntity())
    }
}