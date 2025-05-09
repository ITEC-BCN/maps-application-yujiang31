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

class MainViewModel: ViewModel() {


    // Base de datos Supabase invocacion

    val database = MyApp.database


    // Lista de Mapas
    private val _MapsList = MutableLiveData<List<MapsApp>>()
    val MapsList = _MapsList

    // Mapa selecionada
    private var _selectedMaps: MapsApp? = null

    // Coordenadas al clickar
    val _clickedPosition = MutableStateFlow<LatLng?>(null)
    val clickedPosition= _clickedPosition




    fun updateClickedPosition(latLng: LatLng) {
        _clickedPosition.value = latLng
    }

    private val _MapsName = MutableLiveData<String>()
    val MapsName = _MapsName
    private val _MapsMark = MutableLiveData<String>()
    val MapsMark = _MapsMark







    // Obtener todos los mapas
    fun getAllMaps() {
        CoroutineScope(Dispatchers.IO).launch {
            val databaseStudents = database.getAllMaps()
            withContext(Dispatchers.Main) {
                _MapsList.value = databaseStudents
            }
        }
    }


    // Crear un Nuevo Maps
    fun insertNewMaps(name: String, mark: String, image: String) {
        val newMaps = MapsApp(name = name, mark = mark.toDouble(), image = image)
        CoroutineScope(Dispatchers.IO).launch {
            database.insertMaps(newMaps)
            database.getAllMaps()
        }
    }


    fun updateMaps(id: String, name: String, mark: String, image: Bitmap?){
        val stream = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.PNG, 0, stream)
        val imageName = _selectedMaps?.image?.removePrefix("https://aobflzinjcljzqpxpcxs.supabase.co/storage/v1/object/public/images/")
        CoroutineScope(Dispatchers.IO).launch {

            database.updateMaps(id, name, mark.toDouble(), imageName.toString(), stream.toByteArray())
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
                    _MapsMark.value = Maps.mark.toString()
                }
            }
        }
    }


    fun deleteMaps(id: String, image: String){
        CoroutineScope(Dispatchers.IO).launch {
            database.deleteMaps(id)
            database.deleteImage(image)
            getAllMaps()
        }
    }

    fun editMapsName(name: String) {
        _MapsName.value = name
    }

    fun editMapsMark(mark: String) {
        _MapsMark.value = mark
    }

}