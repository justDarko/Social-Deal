package com.example.core.data.repositories

import com.example.core.data.CustomResult
import com.example.core.data.remote.ApiService
import com.example.core.data.toSocialDeal
import com.example.core.domain.model.SocialDeal
import com.example.core.domain.repositories.SocialDealListRepository
import javax.inject.Inject

class SocialDealListRepositoryImpl @Inject constructor(
    private val apiService: ApiService
    // Add Dao object to cache the results inside Room
) : SocialDealListRepository {
    override suspend fun getSocialDealList(): CustomResult<List<SocialDeal>> {
        return try {
            val response = apiService.getSocialDealsList()
            CustomResult.Success(response.deals.map { it.toSocialDeal() })
        } catch (e: Exception) {
            CustomResult.Failure(e.localizedMessage ?: "Something went wrong")
        }
    }
}