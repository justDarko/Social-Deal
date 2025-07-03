package com.example.core.data.repositories

import com.example.core.data.CustomResult
import com.example.core.data.convertToUSD
import com.example.core.data.local.dataStore.DataStoreManager
import com.example.core.data.local.db.SocialDealsDao
import com.example.core.data.toSocialDeal
import com.example.core.data.toStringWithTwoDecimals
import com.example.core.domain.model.SocialDeal
import com.example.core.domain.repositories.FavoriteSocialDealListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteSocialDealListRepositoryImpl @Inject constructor(
    private val socialDealsDao: SocialDealsDao,
    private val dataStoreManager: DataStoreManager
) : FavoriteSocialDealListRepository {

    override fun getFavoriteSocialDealList(): Flow<CustomResult<List<SocialDeal>>> = flow {
        try {
            // Combine favorites and currency flow inside flow builder
            val combinedFlow = combine(
                socialDealsDao.getFavoriteSocialDeals()
                    .map { list -> list.map { it.toSocialDeal() } },
                dataStoreManager.getUserCurrency().map { result ->
                    when (result) {
                        is CustomResult.Success -> result.data
                        else -> "EUR"
                    }
                }
            ) { favorites, currency ->

                val transformedFavorites = favorites.map { deal ->
                    val newPriceFloat = deal.newPrice.toFloatOrNull() ?: 0f
                    val oldPriceFloat = deal.oldPrice.toFloatOrNull() ?: 0f

                    when (currency) {
                        "USD" -> deal.copy(
                            newPrice = convertToUSD(newPriceFloat).toStringWithTwoDecimals(),
                            oldPrice = convertToUSD(oldPriceFloat).toStringWithTwoDecimals(),
                            currencySign = "$"
                        )

                        "EUR" -> deal.copy(
                            newPrice = newPriceFloat.toStringWithTwoDecimals(),
                            oldPrice = oldPriceFloat.toStringWithTwoDecimals(),
                            currencySign = "€"
                        )

                        else -> deal
                    }
                }

                CustomResult.Success(transformedFavorites)
            }

            emitAll(combinedFlow)

        } catch (e: Exception) {
            emit(CustomResult.Failure(e.localizedMessage ?: "Something went wrong"))
        }
    }

}
