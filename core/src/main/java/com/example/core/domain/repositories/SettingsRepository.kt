package com.example.core.domain.repositories

import com.example.core.data.CustomResult
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setUserCurrency(currency: String)
    fun getUserCurrency(): Flow<CustomResult<String>>
}