package com.example.core.domain.useCase

import com.example.core.domain.repositories.SettingsRepository
import javax.inject.Inject

class SetCurrencyUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : BaseUseCaseNoResult<SetCurrencyUseCase.Params>() {

    override suspend operator fun invoke(params: Params) {
        return settingsRepository.setUserCurrency(
            currency = params.currency
        )
    }

    data class Params(
        val currency: String
    )
}