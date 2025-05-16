package com.example.mapsapp.ui.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mapsapp.ui.navigation.Destinations.Map
import com.example.mapsapp.ui.screens.ListScreen
import com.example.mapsapp.ui.screens.MapScreen
import com.example.mapsapp.ui.navigation.Destinations.List
import com.example.mapsapp.ui.navigation.Destinations.MarkerCreation
import com.example.mapsapp.ui.navigation.Destinations.DetailMap
import com.example.mapsapp.ui.screens.CreateMarkerScreen
import com.example.mapsapp.ui.screens.DetailMarkerScreen

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("WrongNavigateRouteType")
@Composable
fun InternalNavigationWrapper(navController: NavHostController, modifier: Modifier){

    NavHost(navController = navController, startDestination = Map, modifier = modifier) {
        composable<Map> {
            MapScreen{lat, lng ->
                navController.navigate(MarkerCreation(lat, lng))
            }
        }

        composable<List> {
            ListScreen{maps ->
                navController.navigate(DetailMap(maps))

            }
        }

        composable<MarkerCreation> {backStackEntry ->
            val pantallaMarker =backStackEntry.toRoute<MarkerCreation>()
            CreateMarkerScreen(pantallaMarker.lat, pantallaMarker.lng){
                navController.popBackStack()
            }
        }

        composable<DetailMap> { backStackEntry ->
            val pantallaMarker = backStackEntry.toRoute<DetailMap>()
            DetailMarkerScreen(
                id = pantallaMarker.coordenadas,
                navigateBack = { navController.popBackStack() }

            )
        }


    }
}