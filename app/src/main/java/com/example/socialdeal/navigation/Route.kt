package com.example.socialdeal.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object HomeScreen : Route

    @Serializable
    data object DealDetailsScreen : Route

    @Serializable
    data object FavoriteDealsScreen : Route

    @Serializable
    data object SettingsScreen : Route
}