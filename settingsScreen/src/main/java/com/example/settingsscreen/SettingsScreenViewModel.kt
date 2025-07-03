package com.example.settingsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.CustomResult
import com.example.core.domain.useCase.GetCurrencyUseCase
import com.example.core.domain.useCase.SetCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val setCurrencyUseCase: SetCurrencyUseCase
) : ViewModel() {
    init {
        getCurrency()
    }

    private val _currencyState = MutableStateFlow(Currency.EUR.name.uppercase())
    val currencyState = _currencyState.asStateFlow()

    private fun getCurrency() {
        viewModelScope.launch(Dispatchers.IO) {
            getCurrencyUseCase().collect { result ->
                when (result) {
                    is CustomResult.Success -> {
                        Timber.d("Selected currency is ${result.data.uppercase()}")
                        _currencyState.value = result.data
                    }

                    is CustomResult.Failure -> {
                        // Default value -> EUR is returned in this case.
                    }
                }
            }
        }
    }

    fun setCurrency(currency: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setCurrencyUseCase(SetCurrencyUseCase.Params(currency = currency))
        }
    }
}