package com.example.mapsapp

import android.app.Application
import com.example.mapsapp.data.MySupabaseClient

class MyApp:Application() {

    companion object {
        lateinit var database: MySupabaseClient
    }
    override fun onCreate() {
        super.onCreate()
        database = MySupabaseClient(
            supabaseUrl = "https://qfjajgqofnmxmpwetrjf.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InFmamFqZ3FvZm5teG1wd2V0cmpmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDY1OTk4MzQsImV4cCI6MjA2MjE3NTgzNH0.PVv0MWnDdD-xrqaxn4gsHqArTZ53YIvJjXvVuG0GIHo"
        )
    }

}