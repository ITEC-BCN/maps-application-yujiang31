package com.example.mapsapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.viewmodels.MainViewModel
import com.example.mapsapp.viewmodels.PermissionViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapScreen(navigateToMarker:(coordinates:String) -> Unit){

    val myViewModel : MainViewModel = viewModel()
    val clickedPosition by myViewModel.clickedPosition.

    Column(modifier = Modifier.fillMaxSize()) {
        val itb = LatLng(41.4534225, 2.1837151)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(itb, 17f)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(), cameraPositionState = cameraPositionState,
            onMapClick = {
                Log.d("MAP CLICKED", it.toString())
            }, onMapLongClick = {
                Log.d("MAP CLICKED LONG", it.toString())
                myViewModel.updateClickedPosition(it)
                navigateToMarker(it.toString())
            }
        ){
            Marker(
                state = MarkerState(position = itb), title = "ITB",
                snippet = "Marker at ITB"
            )


            clickedPosition?.let{ posit ->
                Marker(
                    state = MarkerState(position = posit),
                    title = "Marcador",
                    snippet = "Creado por ..."
                )
            }
        }


    }

}