package com.example.mapsapp.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from


class MySupabaseClient (){
    lateinit var client: SupabaseClient
    constructor(supabaseUrl: String, supabaseKey: String): this(){
        client = createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseKey
        ){
            install(Postgrest)

        }

    }

    suspend fun getAllMaps(): List<MapsApp> {
        return client.from("MapsApp").select().decodeList<MapsApp>()
    }


    suspend fun getMaps(id: String): MapsApp{
        return client.from("MapsApp").select {
            filter {
                eq("id", id)
            }
        }.decodeSingle<MapsApp>()
    }


    suspend fun insertMaps(student: MapsApp){
        client.from("Student").insert(student)
    }
    suspend fun updateMaps(id: String, name: String, mark: Double){
        client.from("MapsApp").update({
            set("name", name)
            set("mark", mark)
        }) { filter { eq("id", id) } }
    }
    suspend fun deleteMaps(id: String){
        client.from("MapsApp").delete{ filter { eq("id", id) } }
    }

    //SQL operations


}