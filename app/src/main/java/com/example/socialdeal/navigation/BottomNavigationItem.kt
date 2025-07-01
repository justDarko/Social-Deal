package com.example.socialdeal.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val selectedImage: ImageVector,
    val unselectedImage: ImageVector,
    val route: Route
)
