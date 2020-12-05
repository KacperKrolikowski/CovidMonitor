package com.krolikowski.covidmonitor

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.mashape.unirest.http.Unirest
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var selectedCountry = intent.getStringExtra("country").toString()

        updateData(selectedCountry)

        progressBar.max = 1000

        val targetProgress = 1000

        progressBar.progressTintList = ColorStateList.valueOf(Color.GREEN)

        ObjectAnimator.ofInt(progressBar, "progress", targetProgress)
            .setDuration(1000)
            .start()


        change_country_button.setOnClickListener {
            val intent = Intent(applicationContext, CountrySelectorActivity::class.java)
            startActivity(intent)
        }

    }

    private fun updateData(name: String) {

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

                (getString(R.string.country_name) + " " + singleCountryInfo.countryText).also { country.text = it }

                if(singleCountryInfo.activeCasesText == ""){
                    (getString(R.string.active_case) + " " + getString(R.string.no_data)).also { country_active.text = it }
                } else {
                    (getString(R.string.active_case) + " " + singleCountryInfo.activeCasesText).also { country_active.text = it
                    }
                }

                if(singleCountryInfo.newCasesText == ""){
                    (getString(R.string.new_active_case) + " " + getString(R.string.no_data)).also { country_new_case.text = it }
                } else {
                    (getString(R.string.new_active_case) + " " + singleCountryInfo.newCasesText).also { country_new_case.text = it }
                }

                if(singleCountryInfo.totalDeathsText == ""){
                    (getString(R.string.fatal_case) + " " + getString(R.string.no_data)).also { country_deaths.text = it }
                } else {
                    (getString(R.string.fatal_case) + " " + singleCountryInfo.totalDeathsText).also { country_deaths.text = it }
                }

                if(singleCountryInfo.newDeathsText == ""){
                    (getString(R.string.new_fatal_cases) + " " + getString(R.string.no_data)).also { country_new_death.text = it }
                } else {
                    (getString(R.string.new_fatal_cases) + " " + singleCountryInfo.newDeathsText).also { country_new_death.text = it }
                }

                if(singleCountryInfo.totalRecoveredText == ""){
                    (getString(R.string.recovered_cases) + " " + getString(R.string.no_data)).also { country_recovered.text = it }
                } else {
                    (getString(R.string.recovered_cases) + " " + singleCountryInfo.totalRecoveredText).also { country_recovered.text = it }
                }

                (getString(R.string.all_time_confirmed_cases) + " " + singleCountryInfo.totalCasesText).also { country_confirmed.text = it }
                (getString(R.string.last_update) + " " + singleCountryInfo.lastUpdate).also { last_update_data.text = it }

            }

        }
    }
}