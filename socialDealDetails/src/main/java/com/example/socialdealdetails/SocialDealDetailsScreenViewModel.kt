package com.example.socialdealdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.CustomResult
import com.example.core.domain.model.SocialDeal
import com.example.core.domain.useCase.GetSocialDealDetailsUseCase
import com.example.core.domain.useCase.SetFavoriteSocialDealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SocialDealDetailsScreenViewModel @Inject constructor(
    private val getSocialDealDetailsUseCase: GetSocialDealDetailsUseCase,
    private val setFavoriteSocialDealUseCase: SetFavoriteSocialDealUseCase
) : ViewModel() {
    private val _state =
        MutableStateFlow<SocialDealDetailsScreenViewState>(SocialDealDetailsScreenViewState.Loading)
    val state = _state.asStateFlow()

    fun getSocialDealDetails(id: String) {
        viewModelScope.launch {
            getSocialDealDetailsUseCase(GetSocialDealDetailsUseCase.Params(id = id)).collect { result ->
                when (result) {
                    is CustomResult.Success -> {
                        Timber.d("Current social deals: ${result.data}")
                        _state.value =
                            SocialDealDetailsScreenViewState.SocialDealDetails(result.data)
                    }

                    is CustomResult.Failure -> {
                        Timber.d("Current social deals: ${result.message}")
                        _state.value = SocialDealDetailsScreenViewState.Error(result.message)
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