package com.example.mapsapp.data

import kotlinx.serialization.Serializable


@Serializable
data class MapsApp (
    val id :Int? = 0,
    val name :String,
    val mark:String,
    val image: String,
    val latitud:Double,
    val longitud:Double

)

