package com.example.mapsapp.ui.navigation

import kotlinx.serialization.Serializable


sealed class Destinations {


    // Login
    @Serializable
    object Login: Destinations()

    // Registro
    @Serializable
    object Register : Destinations()


    // Permissos
    @Serializable
    object Permissions : Destinations()

    // Drawer (Principal)
    @Serializable
    object Drawer : Destinations()

    // Maps
    @Serializable
    object Map : Destinations()

    // Listado
    @Serializable
    object List : Destinations()

    // Crear nueva ubicacion
    @Serializable
    data class MarkerCreation(val lat:Double, val lng: Double):Destinations()

    // Detalle, Update de un marcador ya creado
    @Serializable
    data class DetailMap(val id: String, val lat: Double, val lng: Double): Destinations()
/*
    @Serializable
    data class MarkerDetails(val id : Int):Destinations
     */

}