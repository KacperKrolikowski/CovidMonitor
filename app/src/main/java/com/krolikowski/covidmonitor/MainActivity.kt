package com.krolikowski.covidmonitor

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var selectedCountry: String = "Poland"
        var currentData: String = "2020-11-30"

        var CovApiKey = BuildConfig.COV_API_KEY

        doAsync {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://covid-19-data.p.rapidapi.com/help/countries")
                .get()
                .addHeader("x-rapidapi-key", CovApiKey)
                .addHeader("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
                .build()

            val response = client.newCall(request).execute()

            Log.d("HTTP", response.toString())

        }
    }
}