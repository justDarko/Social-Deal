package com.example.socialdeal.navigation

import HomeScreen
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.HomeScreen::class.simpleName!!
    ) {
        composable(Route.HomeScreen::class.simpleName!!) {
            HomeScreen(modifier = modifier, onOpenDetails = {
                navController.navigate(Route.DealDetailsScreen::class.simpleName!!)
            })
        }
        composable(Route.DealDetailsScreen::class.simpleName!!) {
            DetailScreen(modifier = modifier, onBackPressed = { navController.popBackStack() })
        }
        composable(Route.FavoriteDealsScreen::class.simpleName!!) {
            FavoriteScreen(modifier = modifier)
        }
        composable(Route.SettingsScreen::class.simpleName!!) {
            SettingsScreen(modifier = modifier)
        }
    }
}

// Temporary Test Screen Composable
@Composable
fun DetailScreen(
    modifier: Modifier, onBackPressed: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Detail",
            modifier.clickable {
                onBackPressed()
            })
    }
}

@Composable
fun FavoriteScreen(
    modifier: Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "Favorite")
    }
}

@Composable
fun SettingsScreen(
    modifier: Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(text = "Settings")
    }
}