package com.example.socialdeal.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupNavigation() {
    val navController = rememberNavController()
    val listOfBottomNavigationItems = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedImage = Icons.Filled.Home,
            unselectedImage = Icons.Outlined.Home,
            route = Route.HomeScreen
        ),
        BottomNavigationItem(
            title = "Favorite",
            selectedImage = Icons.Filled.Favorite,
            unselectedImage = Icons.Outlined.FavoriteBorder,
            route = Route.FavoriteDealsScreen
        ),
        BottomNavigationItem(
            title = "Settings",
            selectedImage = Icons.Filled.Settings,
            unselectedImage = Icons.Outlined.Settings,
            route = Route.SettingsScreen
        ),
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    Timber.d("Arguments route destination: ${currentBackStackEntry?.destination?.route}")

    val currentRoute = currentBackStackEntry?.destination?.route.toRoute()

//    val currentRoute = currentBackStackEntry?.arguments
//        ?.getString("route")
//        ?.let { routeJson.decodeFromString<Route>(it) }

    // Line below throws an error. Could not find solution for it.
    // IllegalArgumentException: Polymorphic value has not been read for class null
//    val currentRoute = currentBackStackEntry?.toRoute<Route>()

    Timber.d("Current route is: $currentRoute")

    val indexOnSelectedItem = when (currentRoute) {
        is Route.HomeScreen -> 0
        is Route.FavoriteDealsScreen -> 1
        is Route.SettingsScreen -> 2
        else -> -1
    }

    Scaffold(modifier = Modifier
        .fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Social Deal"
                )
            })
        },
        bottomBar = {
            // No need to show the bottom bar when we present the Deal Details
            if (currentRoute !is Route.DealDetailsScreen) {
                NavigationBar {
                    listOfBottomNavigationItems.forEachIndexed { index, item ->
                        NavigationBarItem(selected = indexOnSelectedItem == index, onClick = {
                            Timber.d("pressed route: ${item.route}")
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }, icon = {
                            if (indexOnSelectedItem == index) Icon(
                                imageVector = item.selectedImage,
                                contentDescription = "Selected Tab Icon"
                            )
                            else Icon(
                                imageVector = item.unselectedImage,
                                contentDescription = "Unselected Tab Icon"
                            )
                        }, label = {
                            Text(text = item.title)
                        })

                    }
                }
            }
        }
    ) { contentPadding ->
        NavGraph(
            navController = navController, modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        )
    }
}