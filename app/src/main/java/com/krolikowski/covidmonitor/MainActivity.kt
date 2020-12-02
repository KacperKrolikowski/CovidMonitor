package com.krolikowski.covidmonitor

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName
import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import com.krolikowski.covidmonitor.SingleCountry
import com.krolikowski.covidmonitor.SingleCountryItem


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mNameCountryTV: TextView = findViewById(R.id.country_name)
        val mActiveCountryTV: TextView = findViewById(R.id.country_active)
        val mDeathsCountryTV: TextView = findViewById(R.id.country_deaths)
        val mRecoveredCountryTV: TextView = findViewById(R.id.country_recovered)
        val mConfirmedCountryTV: TextView = findViewById(R.id.country_confirmed)

        var selectedCountry = "Poland"
        var currentData = "2020-11-30"

        val covApiKey = BuildConfig.COV_API_KEY

        //[{"country":"Italy","provinces":[{"province":"Italy","confirmed":110574,"recovered":16847,"deaths":13155,"active":80572}],"latitude":41.87194,"longitude":12.56738,"date":"2020-04-01"}]

        doAsync {

            var response =
                    Unirest.get("https://covid-19-data.p.rapidapi.com/report/country/name?date=2020-04-01&name=Italy")
                            .header("x-rapidapi-key", covApiKey)
                            .header("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
                            .asString()

            var countryInfoString = response.body.toString()

            val gson = GsonBuilder().create()
            val singleCountryInfo = gson.fromJson(countryInfoString, SingleCountry::class.java)
            println(countryInfoString)

            activityUiThread {

                mNameCountryTV.text = singleCountryInfo[0].country

            }

        }
    }
}