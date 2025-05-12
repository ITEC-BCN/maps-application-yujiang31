package com.example.mapsapp.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CameraViewModel:ViewModel() {

    var capturedImage = mutableStateOf<Bitmap?>(null)

    fun setImage(bitmap: Bitmap) {
        capturedImage.value = bitmap
    }
    
}

