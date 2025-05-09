package com.example.mapsapp.data

import kotlinx.serialization.Serializable


@Serializable
data class MapsApp (
    val id :Int? = null,
    val name :String,
    val mark:Double,
    val image: String
)

