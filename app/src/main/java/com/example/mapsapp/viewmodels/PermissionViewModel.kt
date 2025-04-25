package com.example.mapsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import com.example.mapsapp.utils.PermissionStatus
import androidx.compose.runtime.mutableStateOf


class PermissionViewModel: ViewModel() {

    private val _permissionsStatus = mutableStateOf<Map<String, PermissionStatus>>(emptyMap())
    val permissionsStatus: State<Map<String, PermissionStatus>> = _permissionsStatus

    fun updatePermissionStatus(permission: String, status: PermissionStatus) {
        _permissionsStatus.value = _permissionsStatus.value.toMutableMap().apply {
            this[permission] = status
        }
    }

}