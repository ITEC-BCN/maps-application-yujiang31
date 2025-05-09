package com.example.mapsapp.ui.navigation

import android.annotation.SuppressLint
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

@SuppressLint("WrongNavigateRouteType")
@Composable
fun InternalNavigationWrapper(navController: NavHostController, modifier: Modifier){

    NavHost(navController = navController, startDestination = Map, modifier = modifier) {
        composable<Map> {
            MapScreen{coordenadas ->
                navController.navigate(MarkerCreation(coordenadas))
            }
        }

        composable<List> {
            ListScreen{maps ->
                navController.navigate(DetailMap(maps))

            }
        }

        composable<MarkerCreation> {backStackEntry ->
            val pantallaMarker =backStackEntry.toRoute<MarkerCreation>()
            CreateMarkerScreen(pantallaMarker.coordenadas){
                navController.popBackStack()
            }
        }

        composable<DetailMap> { bacStackEntry ->
            val pantallaMarker = bacStackEntry.toRoute<DetailMap>()
            DetailMarkerScreen(pantallaMarker.coordenadas){
                navController.popBackStack()
            }

        }


    }
}