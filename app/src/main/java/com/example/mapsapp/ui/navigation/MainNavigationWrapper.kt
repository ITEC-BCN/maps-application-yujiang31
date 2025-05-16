package com.example.mapsapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapsapp.ui.screens.DrawerScreen
import com.example.mapsapp.ui.screens.PermissionsScreen
import com.example.mapsapp.ui.navigation.Destinations.Permissions
import com.example.mapsapp.ui.navigation.Destinations.Drawer



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavigationWrapper(){
    val navController = rememberNavController()

    NavHost(navController, Permissions){
        composable<Permissions>{
            PermissionsScreen{
                navController.navigate(Drawer)
            }
        }

        composable<Drawer> {
            DrawerScreen()
        }

    }

}