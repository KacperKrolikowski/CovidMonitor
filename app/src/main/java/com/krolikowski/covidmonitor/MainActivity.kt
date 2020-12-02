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

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mDataTV: TextView = findViewById(R.id.selected_data)
        val mDataPickerButton: ImageButton = findViewById(R.id.select_data)
        val mUpdateButton: Button = findViewById(R.id.update_button)

        var selectedCountry = "Italy"
        var selectedData = "2020-11-30"

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

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val today = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date: String = formatter.format(today)
        mDataTV.text = date

        updateData(selectedCountry, mDataTV.text.toString())

        mDataPickerButton.setOnClickListener{
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                    ("" + mYear + "-" + (mMonth+1) + "-" + mDay).also { mDataTV.text = it }
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        mUpdateButton.setOnClickListener {
            updateData(selectedCountry, mDataTV.text.toString())
        }

    }

    private fun updateData(name: String, data: String){

        val mNameCountryTV: TextView = findViewById(R.id.country_name)
        val mActiveCountryTV: TextView = findViewById(R.id.country_active)
        val mDeathsCountryTV: TextView = findViewById(R.id.country_deaths)
        val mRecoveredCountryTV: TextView = findViewById(R.id.country_recovered)
        val mConfirmedCountryTV: TextView = findViewById(R.id.country_confirmed)
        val covApiKey = BuildConfig.COV_API_KEY

        doAsync {

            val response =
                Unirest.get("https://covid-19-data.p.rapidapi.com/report/country/name?date=$data&name=$name")
                    .header("x-rapidapi-key", covApiKey)
                    .header("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
                    .asString()

            val countryInfoString = response.body.toString()

            val gson = GsonBuilder().create()
            val singleCountryInfo = gson.fromJson(countryInfoString, SingleCountry::class.java)

            activityUiThread() {

                (getString(R.string.country_name) + singleCountryInfo[0].country).also { mNameCountryTV.text = it }
                (getString(R.string.active_case) + singleCountryInfo[0].provinces[0].active).also { mActiveCountryTV.text = it }
                (getString(R.string.fatal_case) + singleCountryInfo[0].provinces[0].deaths.toString()).also { mDeathsCountryTV.text = it }
                (getString(R.string.recovered_cases) + singleCountryInfo[0].provinces[0].recovered.toString()).also { mRecoveredCountryTV.text = it }
                (getString(R.string.all_time_confirmed_cases) + singleCountryInfo[0].provinces[0].confirmed.toString()).also { mConfirmedCountryTV.text = it }
            }

        }
    }
}