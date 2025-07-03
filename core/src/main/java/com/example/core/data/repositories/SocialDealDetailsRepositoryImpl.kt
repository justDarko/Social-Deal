package com.example.core.data.repositories

import com.example.core.data.CustomResult
import com.example.core.data.convertToUSD
import com.example.core.data.local.dataStore.DataStoreManager
import com.example.core.data.local.db.SocialDealsDao
import com.example.core.data.remote.ApiService
import com.example.core.data.toSocialDeal
import com.example.core.data.toStringWithTwoDecimals
import com.example.core.domain.model.SocialDeal
import com.example.core.domain.repositories.SocialDealDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SocialDealDetailsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val socialDealsDao: SocialDealsDao,
    private val dataStoreManager: DataStoreManager
) : SocialDealDetailsRepository {
    override fun getSocialDealDetails(id: String): Flow<CustomResult<SocialDeal>> = flow {
        try {
            // Fetch remote details once (outside the combined flow)
            val response = apiService.getSocialDealDetails(id)
            val description = response.description ?: ""

            // Combine local DB entity flow and user currency flow
            val combinedFlow = dataStoreManager.getUserCurrency().map { result ->
                when (result) {
                    is CustomResult.Success -> result.data
                    else -> "EUR"
                }
            }.let { currencyFlow ->

                socialDealsDao.getSocialDealById(id).combine(currencyFlow) { entity, currency ->
                    val deal = entity.toSocialDeal().copy(description = description)

                    val newPriceFloat = deal.newPrice.toFloatOrNull() ?: 0f
                    val oldPriceFloat = deal.oldPrice.toFloatOrNull() ?: 0f

                    val transformedDeal = when (currency) {
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

                    CustomResult.Success(transformedDeal)
                }
            }

            emitAll(combinedFlow)

        } catch (e: Exception) {
            emit(CustomResult.Failure(e.localizedMessage ?: "Error fetching deal details"))
        }
    }

}