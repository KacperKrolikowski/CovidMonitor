package com.krolikowski.covidmonitor

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mashape.unirest.http.Unirest
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mTextView: TextView = findViewById(R.id.textView)

        var selectedCountry = "Poland"
        var currentData = "2020-11-30"

        val covApiKey = BuildConfig.COV_API_KEY

        doAsync {

            var response =
                    Unirest.get("https://covid-19-data.p.rapidapi.com/report/country/name?date=2020-04-01&name=Italy")
                            .header("x-rapidapi-key", "c9016797e3mshcb3f479b86fc845p1fcc9djsnd7a2f17e2f37")
                            .header("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
                            .asString()


            var all = response.body

            val allres = JSONObject(all).getString("country")
            activityUiThread {
                mTextView.text = allres.toString()
            }

        }
    }
}