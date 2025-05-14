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


    fun updateClickedPosition(latLng: LatLng) {
        _clickedPosition.value = latLng
    }




    // Obtener todos los mapas
    fun getAllMaps() {
        CoroutineScope(Dispatchers.IO).launch {
            val databaseMaps = database.getAllMaps()
            withContext(Dispatchers.Main) {
                _MapsList.value = databaseMaps
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


    fun updateMaps(id: String, name: String, mark: String, image: Bitmap?){
        val stream = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.PNG, 0, stream)
        val imageName = _selectedMaps?.image?.removePrefix("https://aobflzinjcljzqpxpcxs.supabase.co/storage/v1/object/public/images/")
        CoroutineScope(Dispatchers.IO).launch {

            database.updateMaps(id, name, mark, imageName.toString(), stream.toByteArray())
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


    fun deleteMaps(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            database.deleteMaps(id)
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