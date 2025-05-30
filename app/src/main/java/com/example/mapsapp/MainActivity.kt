    package com.example.mapsapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.mapsapp.ui.navigation.InternalNavigationWrapper
import com.example.mapsapp.ui.navigation.MainNavigationWrapper
import com.example.mapsapp.ui.screens.CameraScreen
import com.example.mapsapp.ui.screens.CreateMarkerScreen
import com.example.mapsapp.ui.screens.DrawerScreen
import com.example.mapsapp.ui.screens.MapScreen
import com.example.mapsapp.ui.screens.PermissionsScreen
import com.example.mapsapp.ui.theme.MapsAppTheme
import com.example.mapsapp.viewmodels.PermissionViewModel


    class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MapsAppTheme (){
                MainNavigationWrapper()
            }
        }
    }
}

