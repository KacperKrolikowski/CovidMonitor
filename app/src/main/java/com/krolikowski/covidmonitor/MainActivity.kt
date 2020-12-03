package com.krolikowski.covidmonitor

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.mashape.unirest.http.Unirest
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.sin

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mUpdateButton: Button = findViewById(R.id.update_button)

        var selectedCountry = "Italy"

        {
        //[{"country":"Italy","provinces":[{"province":"Italy","confirmed":110574,"recovered":16847,"deaths":13155,"active":80572}],"latitude":41.87194,"longitude":12.56738,"date":"2020-04-01"}]

        /*doAsync {

            val response =
                    Unirest.get("https://covid-19-data.p.rapidapi.com/report/country/name?date=2020-04-01&name=Italy")
                            .header("x-rapidapi-key", covApiKey)
                            .header("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
                            .asString()

            val countryInfoString = response.body.toString()

            val gson = GsonBuilder().create()
            val singleCountryInfo = gson.fromJson(countryInfoString, SingleCountry::class.java)
            println(countryInfoString)

            activityUiThread {

                (getString(R.string.country_name) + singleCountryInfo[0].country).also { mNameCountryTV.text = it }
                (getString(R.string.active_case) + singleCountryInfo[0].provinces[0].active).also { mActiveCountryTV.text = it }
                (getString(R.string.fatal_case) + singleCountryInfo[0].provinces[0].deaths.toString()).also { mDeathsCountryTV.text = it }
                (getString(R.string.recovered_cases) + singleCountryInfo[0].provinces[0].recovered.toString()).also { mRecoveredCountryTV.text = it }
                (getString(R.string.all_time_confirmed_cases) + singleCountryInfo[0].provinces[0].confirmed.toString()).also { mConfirmedCountryTV.text = it }
                mDataTV.text = currentData
            }

        }*/
    }

        updateData(selectedCountry)

        mUpdateButton.setOnClickListener {
            updateData("USA")
        }

    }

    private fun updateData(name: String){

        val mNameCountryTV: TextView = findViewById(R.id.country_name)
        val mActiveCountryTV: TextView = findViewById(R.id.country_active)
        val mNewActiveCountryTV: TextView = findViewById(R.id.country_new_case)
        val mDeathsCountryTV: TextView = findViewById(R.id.country_deaths)
        val mNewDeathCountryTV: TextView = findViewById(R.id.country_new_death)
        val mRecoveredCountryTV: TextView = findViewById(R.id.country_recovered)
        val mConfirmedCountryTV: TextView = findViewById(R.id.country_confirmed)
        val mDataTV: TextView = findViewById(R.id.last_update_data)
        val covApiKey = BuildConfig.COV_API_KEY

        doAsync {

            val response =
                Unirest.get("https://covid-19-tracking.p.rapidapi.com/v1/$name")
                    .header("x-rapidapi-key", covApiKey)
                    .header("x-rapidapi-host", "covid-19-tracking.p.rapidapi.com")
                    .asString()

            val countryInfoString = response.body.toString()

            val gson = GsonBuilder().create()
            val singleCountryInfo = gson.fromJson(countryInfoString, SingleCountry::class.java)

            activityUiThread {

                (getString(R.string.country_name)+ " " + singleCountryInfo.countryText).also { mNameCountryTV.text = it }
                (getString(R.string.active_case)+ " " + singleCountryInfo.activeCasesText).also { mActiveCountryTV.text = it }
                (getString(R.string.new_active_case)+ " " + singleCountryInfo.newCasesText).also { mNewActiveCountryTV.text = it }
                (getString(R.string.fatal_case)+ " " + singleCountryInfo.totalDeathsText).also { mDeathsCountryTV.text = it }
                (getString(R.string.new_fatal_cases)+ " " + singleCountryInfo.newDeathsText).also { mNewDeathCountryTV.text = it }
                (getString(R.string.recovered_cases)+ " " + singleCountryInfo.totalRecoveredText).also { mRecoveredCountryTV.text = it }
                (getString(R.string.all_time_confirmed_cases)+ " " + singleCountryInfo.totalCasesText).also { mConfirmedCountryTV.text = it }
                (getString(R.string.last_update)+ " " + singleCountryInfo.lastUpdate).also { mDataTV.text = it }

            }

        }
    }
}