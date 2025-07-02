package com.example.socialdeallist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.CustomResult
import com.example.core.domain.model.SocialDeal
import com.example.core.domain.useCase.GetSocialDealListUseCase
import com.example.core.domain.useCase.SetFavoriteSocialDealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getSocialDealListUseCase: GetSocialDealListUseCase,
    private val setFavoriteSocialDealUseCase: SetFavoriteSocialDealUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<HomeScreenViewState>(HomeScreenViewState.Loading)
    val state = _state.asStateFlow()

    init {
        getSocialDealList()
    }

    private fun getSocialDealList() {
        viewModelScope.launch {
            getSocialDealListUseCase()
                // Optional: flowOn(IO) inside use case or here if needed
                .collect { result ->
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

    fun setFavoriteSocialDeal(socialDeal: SocialDeal) {
        Timber.d("Setting favorite social deal with id: ${socialDeal.id}")
        viewModelScope.launch(Dispatchers.IO) {
            setFavoriteSocialDealUseCase(SetFavoriteSocialDealUseCase.Params(socialDeal))
        }
    }
}