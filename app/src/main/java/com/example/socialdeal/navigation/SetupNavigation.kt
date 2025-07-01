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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController

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

    val currentBackStackEntry by navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(
        null
    )
    val currentRoute = currentBackStackEntry?.destination?.route
    var indexOnSelectedItem = when (currentRoute) {
        Route.HomeScreen::class.simpleName -> 0
        Route.FavoriteDealsScreen::class.simpleName -> 1
        Route.SettingsScreen::class.simpleName -> 2
        else -> -1
    }

    // Needed for the TopAppBar for smooth scroll experience
    val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(scrollBehaviour.nestedScrollConnection),

        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Social Deal"
                )
            }, scrollBehavior = scrollBehaviour)
        },
        bottomBar = {
            // No need to show the bottom bar when we present the Deal Details
            if (currentRoute != Route.DealDetailsScreen::class.simpleName) {
                NavigationBar {
                    listOfBottomNavigationItems.forEachIndexed { index, item ->
                        NavigationBarItem(selected = indexOnSelectedItem == index, onClick = {
                            indexOnSelectedItem = index
                            val route = item.route::class.simpleName!!
                            navController.navigate(route) {
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
        }) { contentPadding ->
        NavGraph(
            navController = navController, modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        )
    }
}