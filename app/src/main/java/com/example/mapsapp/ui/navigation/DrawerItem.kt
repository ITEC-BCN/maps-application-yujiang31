package com.example.mapsapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector

enum class DrawerItem(
    val icon: ImageVector,
    val text: String,
    val route: Destinations
){
    Map(Icons.Default.Place, "Map", Destinations.Map),
    List(Icons.Default.List, "List", Destinations.List),
}