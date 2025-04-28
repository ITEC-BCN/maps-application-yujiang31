package com.example.mapsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapsapp.ui.screens.DrawerScreen
import com.example.mapsapp.ui.screens.PermissionsScreen
import com.example.mapsapp.ui.navigation.Destinations.Permissions
import com.example.mapsapp.ui.navigation.Destinations.Drawer



@Composable
fun MainNavigationWrapper(){
    val navController = rememberNavController()

    NavHost(navController, Permissions){
        composable<Permissions>{
            PermissionsScreen{
                navController.navigate(Drawer){
                    popUpTo(Permissions){inclusive = true}
                }
            }
        }


        composable<Drawer> {

            DrawerScreen {
                navController.navigate(Drawer)
            }
        }

    }

}