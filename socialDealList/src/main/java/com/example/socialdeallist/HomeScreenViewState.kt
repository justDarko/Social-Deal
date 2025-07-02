package com.example.socialdeallist

import com.example.core.domain.model.SocialDeal

sealed class HomeScreenViewState {
    data class SocialDealsList(val socialDealsList: List<SocialDeal>) : HomeScreenViewState()
    data object Loading : HomeScreenViewState()
    data class Error(val message: String) : HomeScreenViewState()

}
