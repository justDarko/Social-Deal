package com.example.socialdeal.navigation

// Aware that this is not the best possible solution.
// This is a workaround. My intention was to use only Type-Safe navigation
// with @Serializable and not use hardcoded strings like this.
// Problem i encountered was this:
// IllegalArgumentException: Polymorphic value has not been read for class null
fun String?.toRoute(): Route = when (this) {
    "com.example.socialdeal.navigation.Route.HomeScreen" -> Route.HomeScreen
    "com.example.socialdeal.navigation.Route.FavoriteDealsScreen" -> Route.FavoriteDealsScreen
    "com.example.socialdeal.navigation.Route.SettingsScreen" -> Route.SettingsScreen
    "com.example.socialdeal.navigation.Route.DealDetailsScreen/{id}" -> Route.DealDetailsScreen(id = "")
    else -> Route.HomeScreen // fallback or unknown route handler
}