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

        var selectedCountry = "Poland"
        var currentData = "2020-11-30"

        val covApiKey = BuildConfig.COV_API_KEY

        doAsync {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://covid-19-data.p.rapidapi.com/report/country/name?date=$currentData&name=$selectedCountry")
                .get()
                .addHeader("x-rapidapi-key", covApiKey)
                .addHeader("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
                .build()

            val response = client.newCall(request).execute()

            Log.d("HTTP", response.toString())

        }
    }
}