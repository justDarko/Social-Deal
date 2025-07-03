package com.example.core.domain.repositories

import com.example.core.data.CustomResult
import com.example.core.domain.model.SocialDeal
import kotlinx.coroutines.flow.Flow

interface SocialDealDetailsRepository {
    fun getSocialDealDetails(id: String): Flow<CustomResult<SocialDeal>>
}