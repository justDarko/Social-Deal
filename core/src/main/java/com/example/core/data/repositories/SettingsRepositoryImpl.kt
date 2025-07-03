package com.example.core.data.repositories

import com.example.core.data.CustomResult
import com.example.core.data.local.dataStore.DataStoreManager
import com.example.core.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : SettingsRepository {
    override suspend fun setUserCurrency(currency: String) {
        dataStoreManager.setUserCurrency(currency)
    }

    override fun getUserCurrency(): Flow<CustomResult<String>> {
        return dataStoreManager.getUserCurrency()
            .map { result ->
                when (result) {
                    is CustomResult.Success -> result
                    is CustomResult.Failure -> CustomResult.Success("EUR")
                }
            }
    }

}