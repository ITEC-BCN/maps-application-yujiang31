package com.example.mapsapp.data

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mapsapp.BuildConfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime



class MySupabaseClient {
    lateinit var storage: Storage
    lateinit var client: SupabaseClient
    private val supabaseUrl = BuildConfig.SUPABASE_URL
    private val supabaseKey = BuildConfig.SUPABASE_KEY

    constructor(){
        client = createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseKey
        ){
            install(Postgrest)
            install(Storage)
        }

        storage = client.storage
    }


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun uploadImage(imageFile: ByteArray): String {
        val fechaHoraActual = LocalDateTime.now()
        val formato = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        val imageName = storage.from("images").upload(path = "image_${fechaHoraActual.format(formato)}.png", data = imageFile)
        return buildImageUrl(imageFileName = imageName.path)
    }

    fun buildImageUrl(imageFileName: String) = "${this.supabaseUrl}/storage/v1/object/public/images/${imageFileName}"

    suspend fun getAllMaps(): List<MapsApp> {
        return client.from("maps").select().decodeList<MapsApp>()
    }


    suspend fun getMaps(id: String): MapsApp{
        return client.from("maps").select {
            filter {
                eq("id", id)
            }
        }.decodeSingle<MapsApp>()
    }


    suspend fun insertMaps(student: MapsApp){
        client.from("maps").insert(student)
    }


    suspend fun updateMaps(id: String, name: String, mark: String, imagename:String, imageFile: ByteArray){
        val imageName = storage.from("images").update(path = imagename, data = imageFile)

        client.from("maps").update({
            set("name", name)
            set("mark", mark)
            set("image", buildImageUrl(imageFileName = imageName.path))
        }) {
            filter {
                eq("id", id)
            }
        }
    }
    suspend fun deleteMaps(id: String){
        client.from("maps").delete{ filter { eq("id", id) } }
    }

    //SQL operations


    suspend fun deleteImage(imageName: String){
        val imgName = imageName.removePrefix("https://aobflzinjcljzqpxpcxs.supabase.co/storage/v1/object/public/images/")
        client.storage.from("images").delete(imgName)
    }

}