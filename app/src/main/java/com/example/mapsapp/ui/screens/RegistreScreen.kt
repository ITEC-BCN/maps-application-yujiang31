package com.example.mapsapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.utils.SharedPreferencesHelper
import com.example.mapsapp.viewmodels.AuthViewModelFactory
import com.example.mapsapp.viewmodels.MainViewModel

@Composable
fun RegistreScreen(


){


    val context = LocalContext.current
    val viewModel: MainViewModel = viewModel(factory = AuthViewModelFactory(SharedPreferencesHelper(context)))
    val authState by viewModel.authState.observeAsState()
    val showError by viewModel.showError.observeAsState()
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")



}