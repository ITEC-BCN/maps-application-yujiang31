package com.example.mapsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mapsapp.utils.SharedPreferencesHelper
import com.example.mapsapp.viewmodels.MainViewModel

/*
class AuthViewModelFactory(private val shredPreferences: SharedPreferencesHelper): ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModel::class.java)) {
            return MainViewModel(shredPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


 */