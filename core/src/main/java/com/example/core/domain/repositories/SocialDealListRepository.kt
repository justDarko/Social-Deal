package com.example.core.domain.repositories

import com.example.core.data.CustomResult
import com.example.core.domain.model.SocialDeal

interface SocialDealListRepository {
    suspend fun getSocialDealList(): CustomResult<List<SocialDeal>>
}