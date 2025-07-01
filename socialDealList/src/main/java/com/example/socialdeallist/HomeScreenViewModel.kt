package com.example.socialdeallist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.CustomResult
import com.example.core.domain.useCase.GetSocialDealListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getSocialDealListUseCase: GetSocialDealListUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<HomeScreenViewState>(HomeScreenViewState.Loading)
    val state = _state.asStateFlow()

    init {
        getSocialDealList()
    }

    private fun getSocialDealList() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getSocialDealListUseCase()

            // Switch to Main thread for UI state updates
            withContext(Dispatchers.Main) {
                when (result) {
                    is CustomResult.Success -> {
                        Timber.d("Current social deals: ${result.data}")
                        Timber.d("Current social deals size: ${result.data.size}")

                        _state.value = HomeScreenViewState.SocialDealsList(result.data)
                    }

                    is CustomResult.Failure -> {
                        Timber.d("Current social deals: ${result.message}")

                        _state.value = HomeScreenViewState.Error(result.message)
                    }
                }
            }
        }
    }

}