package com.example.core.domain.repositories

import com.example.core.data.CustomResult
import com.example.core.domain.model.SocialDeal
import kotlinx.coroutines.flow.Flow

interface SocialDealListRepository {
    fun getSocialDealList(): Flow<CustomResult<List<SocialDeal>>>
    suspend fun setFavoriteSocialDeal(socialDeal: SocialDeal)
}