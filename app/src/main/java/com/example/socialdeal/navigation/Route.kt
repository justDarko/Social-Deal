package com.example.socialdeal.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object HomeScreen : Route

    @Serializable
    data class DealDetailsScreen(val id: String) : Route

    @Serializable
    data object FavoriteDealsScreen : Route

    @Serializable
    data object SettingsScreen : Route
}
