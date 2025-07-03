package com.example.socialdealdetails

import com.example.core.domain.model.SocialDeal

sealed class SocialDealDetailsScreenViewState {
    data class SocialDealDetails(val socialDealDetails: SocialDeal) :
        SocialDealDetailsScreenViewState()

    data object Loading : SocialDealDetailsScreenViewState()
    data class Error(val message: String) : SocialDealDetailsScreenViewState()

}
