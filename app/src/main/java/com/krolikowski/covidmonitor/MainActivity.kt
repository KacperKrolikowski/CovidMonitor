package com.krolikowski.covidmonitor

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.mashape.unirest.http.Unirest
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        country_recycle_view.layoutManager = LinearLayoutManager(this)
        country_recycle_view.adapter

        country_list_fab.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if(country_recycle_view.isVisible) {
                    country_recycle_view.visibility = View.INVISIBLE
                } else {
                    country_recycle_view.visibility = View.VISIBLE
                }
            }
        })

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

        update_button.setOnClickListener {
            updateData("USA")
        }

    }

    private fun updateData(name: String){

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

                (getString(R.string.country_name)+ " " + singleCountryInfo.countryText).also { country_name.text = it }
                (getString(R.string.active_case)+ " " + singleCountryInfo.activeCasesText).also { country_active.text = it }
                (getString(R.string.new_active_case)+ " " + singleCountryInfo.newCasesText).also { country_new_case.text = it }
                (getString(R.string.fatal_case)+ " " + singleCountryInfo.totalDeathsText).also { country_deaths.text = it }
                (getString(R.string.new_fatal_cases)+ " " + singleCountryInfo.newDeathsText).also { country_new_death.text = it }
                (getString(R.string.recovered_cases)+ " " + singleCountryInfo.totalRecoveredText).also { country_recovered.text = it }
                (getString(R.string.all_time_confirmed_cases)+ " " + singleCountryInfo.totalCasesText).also { country_confirmed.text = it }
                (getString(R.string.last_update)+ " " + singleCountryInfo.lastUpdate).also { last_update_data.text = it }

            }

        }
    }
}