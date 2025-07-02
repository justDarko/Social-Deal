package com.example.favoritesocialdeallist

import com.example.core.domain.model.SocialDeal

sealed class FavoritesScreenViewState {
    data class FavoriteSocialDealsList(val socialDealsList: List<SocialDeal>) : FavoritesScreenViewState()
    data object Loading : FavoritesScreenViewState()
    data class Error(val message: String) : FavoritesScreenViewState()

}
