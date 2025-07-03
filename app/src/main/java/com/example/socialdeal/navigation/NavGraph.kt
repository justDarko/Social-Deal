package com.example.socialdeal.navigation

import FavoritesScreen
import HomeScreen
import SocialDealDetailsScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.settingsscreen.SettingsScreen


@Composable
fun NavGraph(
    navController: NavHostController, modifier: Modifier
) {
    NavHost(
        navController = navController, startDestination = Route.HomeScreen
    ) {
        composable<Route.HomeScreen> {
            HomeScreen(modifier = modifier, onOpenDetails = { id ->
                navController.navigate(Route.DealDetailsScreen(id = id))
            })
        }
        composable<Route.DealDetailsScreen> { backStackEntry ->
            val details: Route.DealDetailsScreen = backStackEntry.toRoute()
            SocialDealDetailsScreen(id = details.id, modifier = modifier)
        }
        composable<Route.FavoriteDealsScreen> {
            FavoritesScreen(modifier = modifier, onOpenDetails = { id ->
                navController.navigate(Route.DealDetailsScreen(id = id))
            })
        }
        composable<Route.SettingsScreen> {
            SettingsScreen(modifier = modifier)
        }
    }
}