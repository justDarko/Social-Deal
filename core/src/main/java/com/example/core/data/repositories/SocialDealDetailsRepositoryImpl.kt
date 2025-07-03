package com.example.core.data.repositories

import com.example.core.data.CustomResult
import com.example.core.data.local.db.SocialDealsDao
import com.example.core.data.remote.ApiService
import com.example.core.data.toSocialDeal
import com.example.core.domain.model.SocialDeal
import com.example.core.domain.repositories.SocialDealDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SocialDealDetailsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val socialDealsDao: SocialDealsDao
) : SocialDealDetailsRepository {
    override fun getSocialDealDetails(id: String): Flow<CustomResult<SocialDeal>> = flow {
        try {
            // Fetch remote details once
            val response = apiService.getSocialDealDetails(id)
            val description = response.description ?: ""

            // Emit combined flow with updated description from remote
            emitAll(
                socialDealsDao.getSocialDealById(id)
                    .map { entity ->
                        entity?.toSocialDeal()?.copy(description = description)
                            ?.let { CustomResult.Success(it) }
                            ?: CustomResult.Failure("Deal not found locally")
                    }
            )
        } catch (e: Exception) {
            emit(CustomResult.Failure(e.localizedMessage ?: "Error fetching deal details"))
        }
    }
}