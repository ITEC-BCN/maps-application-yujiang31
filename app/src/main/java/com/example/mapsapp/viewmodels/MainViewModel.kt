package com.example.mapsapp.viewmodels

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapsapp.MyApp
import com.example.mapsapp.data.MapsApp
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.mapsapp.utils.AuthState
import com.example.mapsapp.utils.SharedPreferencesHelper
import kotlin.math.ln


class MainViewModel(): ViewModel() {


    // Base de datos Supabase invocacion

    val database = MyApp.database


    /*
    init {
        checkExistingSession()
    }


     */


    // Lista de Mapas
    private val _MapsList = MutableLiveData<List<MapsApp>>()
    val MapsList = _MapsList

    // Mapa selecionada
    private var _selectedMaps: MapsApp? = null

    // Coordenadas al clickar
    val _clickedPosition = MutableStateFlow<LatLng?>(null)
    val clickedPosition= _clickedPosition

    // Variables guardar Datos del Marcador
    private val _MapsName = MutableLiveData<String>()
    val MapsName = _MapsName

    private val _MapsMark = MutableLiveData<String>()
    val MapsMark = _MapsMark

    private val _Image = MutableLiveData<Bitmap?>()
    val Image = _Image

    private val _latitud = MutableLiveData<Double>()
    val latitud = _latitud

    private val _longitud = MutableLiveData<Double>()
    val longitud = _longitud



    // User
    private val authManager = MyApp.database
    private val _email = MutableLiveData<String>()
    val email = _email
    private val _password = MutableLiveData<String>()
    val password = _password
    private val _authState = MutableLiveData<AuthState>()
    val authState = _authState
    private val _showError = MutableLiveData<Boolean>(false)
    val showError = _showError
    private val _user = MutableLiveData<String?>()
    val user = _user


    /*
    private fun checkExistingSession() {
        viewModelScope.launch {
            val accessToken = sharedPreferences.getAccessToken()
            val refreshToken = sharedPreferences.getRefreshToken()
            when {
                !accessToken.isNullOrEmpty() -> refreshToken()
                !refreshToken.isNullOrEmpty() -> refreshToken()
                else -> _authState.value = AuthState.Unauthenticated
            }
        }
    }


    fun signUp() {
        viewModelScope.launch {
            _authState.value = authManager.signUpWithEmail(_email.value!!, _password.value!!)
            if (_authState.value is AuthState.Error) {
                _showError.value = true
            } else {
                val session = authManager.retrieveCurrentSession()
                sharedPreferences.saveAuthData(
                    session!!.accessToken,
                    session.refreshToken
                )
            }
        }
    }

    fun signIn() {
        viewModelScope.launch {
            _authState.value = authManager.signInWithEmail(_email.value!!, _password.value!!)
            if (_authState.value is AuthState.Error) {
                _showError.value = true
            } else {
                val session = authManager.retrieveCurrentSession()
                sharedPreferences.saveAuthData(
                    session!!.accessToken,
                    session.refreshToken
                )
            }
        }
    }


    fun logout() {
        viewModelScope.launch {
            sharedPreferences.clear()
            _authState.value = AuthState.Unauthenticated
        }
    }



    private fun refreshToken() {
        viewModelScope.launch {
            try {
                database.refreshSession()
                _authState.value = AuthState.Authenticated
            } catch (e: Exception) {
                sharedPreferences.clear()
                _authState.value = AuthState.Unauthenticated
            }
        }
    }


*/


    fun editEmail(value: String) {
        _email.value = value
    }

    fun editPassword(value: String) {
        _password.value = value
    }



    fun updateClickedPosition(latLng: LatLng) {
        _clickedPosition.value = latLng
    }




    // Obtener todos los mapas
    fun getAllMaps() {
        viewModelScope.launch(Dispatchers.IO) {
            val maps = database.getAllMaps()
            withContext(Dispatchers.Main) {
                _MapsList.value = maps
            }
        }
    }


    // Crear un Nuevo Maps se ven logs ya que en el momento se usa para comprobar si llegaba a pasar
    // la info correctamente
    @RequiresApi(Build.VERSION_CODES.O)
    fun insertNewMaps(name: String, mark: String, image: Bitmap?, lat: Double, lng:Double) {
        CoroutineScope(Dispatchers.IO).launch {
            val stream = ByteArrayOutputStream()
            image?.compress(Bitmap.CompressFormat.PNG, 0, stream)

            Log.d("Yujiang", "name==null:${name}")
            Log.d("Yujiang", "mark/description==null:${mark}")
            Log.d("Yujiang", "image==null:${image== null}")
            Log.d("Yujiang", "stream.size:${stream.size()}")
            Log.d("Yujiang", "lat/lng.size: $lat/$lng")
            val imageName = database.uploadImage(stream.toByteArray())
            database.insertMaps(MapsApp( name = name, mark = mark, image = imageName, latitud = lat, longitud = lng))
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun updateMaps(id: String, name: String, mark: String, image: Bitmap?, lat: Double, lng: Double) {

        Log.d("Yujiang", "lat.value = $lat")
        Log.d("Yujiang", "lat.value = $lng")
        Log.d("Yujiang", "id.value = $id")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Eliminar imagen y mapa antiguo
                database.deleteMaps(id)
                database.deleteImage(id)
                Log.d("Yujiang", "llego aqui 2")
                // Preparar nueva imagen
                val stream = ByteArrayOutputStream()
                image?.compress(Bitmap.CompressFormat.PNG, 0, stream)
                val imageName = database.uploadImage(stream.toByteArray())

                // Insertar nuevo mapa
                database.insertMaps(
                    MapsApp(
                        name = name,
                        mark = mark,
                        image = imageName,
                        latitud = lat,
                        longitud = lng
                    )
                )

                // Recargar lista de mapas
                getAllMaps()
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error updating map", e)
            }
        }
    }




    // Obtener un Mapa Especifico por el id
    fun getMaps(id: String){
        if(_selectedMaps == null){
            CoroutineScope(Dispatchers.IO).launch {
                val Maps = database.getMaps(id)
                withContext(Dispatchers.Main) {
                    _selectedMaps = Maps
                    _MapsName.value = Maps.name
                    _MapsMark.value = Maps.mark
                }
            }
        }
    }


    fun deleteMaps(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            database.deleteMaps(id)
            database.deleteImage(id)
            getAllMaps()
        }
    }

    fun editMapsName(name: String) {
        _MapsName.value = name
    }

    fun editMapsMark(mark: String) {
        _MapsMark.value = mark
    }


    fun errorMessageShowed(){
        _showError.value = false
    }

}