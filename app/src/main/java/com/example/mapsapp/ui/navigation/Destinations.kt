package com.example.mapsapp.ui.navigation

import kotlinx.serialization.Serializable


sealed class Destinations {
    @Serializable
    object Permissions : Destinations()

    @Serializable
    object Drawer : Destinations()

    @Serializable
    object Map : Destinations()

    @Serializable
    object List : Destinations()

    @Serializable
    data class MarkerCreation(val lat:Double, val lng: Double):Destinations()

    @Serializable
    data class DetailMap(val id: String, val lat: Double, val lng: Double): Destinations()
/*
    @Serializable
    data class MarkerDetails(val id : Int):Destinations
     */

}