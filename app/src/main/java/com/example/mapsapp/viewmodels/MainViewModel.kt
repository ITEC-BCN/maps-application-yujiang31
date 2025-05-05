package com.example.mapsapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {

    val _clickedPosition = MutableStateFlow<LatLng?>(null)
    val clickedPosition= _clickedPosition

    fun updateClickedPosition(latLng: LatLng) {
        _clickedPosition.value = latLng
    }
}