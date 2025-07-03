package com.example.core.domain.useCase

import com.example.core.data.CustomResult
import com.example.core.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : BaseUseCaseFlowNoParams<String>() {

    override fun invoke(): Flow<CustomResult<String>> {
        return settingsRepository.getUserCurrency()
    }
}