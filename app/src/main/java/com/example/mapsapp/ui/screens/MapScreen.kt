package com.example.mapsapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState


@Composable
fun MapScreen(navigateToMarker:(Double, Double) -> Unit){

    val myViewModel : MainViewModel = viewModel()
    val clickedPosition by myViewModel.clickedPosition.collectAsState()

    // Lista de mapas
    val mapsList by myViewModel.MapsList.observeAsState(emptyList())


    LaunchedEffect(Unit) {
        myViewModel.getAllMaps()
    }
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
                navigateToMarker(it.latitude, it.longitude)
            }
        ){

            // Marcador ITB
            Marker(
                state = MarkerState(position = itb), title = "ITB",
                snippet = "Marker at ITB"
            )

            // Pintar el mapa de los marcadores guardados en la BBDD
            mapsList.forEach { map ->
                Marker(
                    state = MarkerState(position = LatLng(map.latitud, map.longitud)),
                    title = map.name,
                    snippet = map.mark
                )
            }


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