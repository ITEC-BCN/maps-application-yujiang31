package com.example.mapsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapsapp.ui.navigation.Destinations.Map
import com.example.mapsapp.ui.screens.ListScreen
import com.example.mapsapp.ui.screens.MapScreen
import com.example.mapsapp.ui.navigation.Destinations.List

@Composable
fun InternalNavigationWrapper(navController: NavHostController, modifier: Modifier){

    NavHost(navController = navController, startDestination = Map, modifier = modifier) {
        composable<Map> {
            MapScreen()
        }

        composable<List> {
            ListScreen()
        }

    }
}