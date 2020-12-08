package com.krolikowski.covidmonitor

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.GsonBuilder
import com.mashape.unirest.http.Unirest
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import java.util.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private val introSliderAdapter = IntroSliderAdapter(
        listOf(
            IntroSlide(
                R.raw.disctance
            ),
            IntroSlide(
                R.raw.hand_wash
            ),
            IntroSlide(
                R.raw.stay_home
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var currentPage = 0

        viewPager.adapter = introSliderAdapter
        setupIndicators()
        setCurrentIndicator(0)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        val handler = Handler()
        val update = Runnable {
            if (currentPage == introSliderAdapter.itemCount) {
                currentPage = 0
            }

            //The second parameter ensures smooth scrolling
            viewPager.setCurrentItem(currentPage++, true)
        }

        Timer().schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                handler.post(update)
            }
        }, 0, 3800)

        val selectedCountry = intent.getStringExtra("country").toString()

        updateData(selectedCountry)

        progressBar.max = 1000

        val targetProgress = 1000

        progressBar.progressTintList = ColorStateList.valueOf(resources.getColor(R.color.green))

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

                (getString(R.string.country_name) + " " + singleCountryInfo.countryText).also {
                    country.text = it
                }

                if (singleCountryInfo.activeCasesText == "") {
                    (getString(R.string.active_case) + " " + getString(R.string.no_data)).also {
                        country_active.text = it
                    }
                } else {
                    (getString(R.string.active_case) + " " + singleCountryInfo.activeCasesText).also {
                        country_active.text = it
                    }
                }

                if (singleCountryInfo.newCasesText == "") {
                    (getString(R.string.new_active_case) + " " + getString(R.string.no_data)).also {
                        country_new_case.text = it
                    }
                } else {
                    (getString(R.string.new_active_case) + " " + singleCountryInfo.newCasesText).also {
                        country_new_case.text = it
                    }
                }

                if (singleCountryInfo.totalDeathsText == "") {
                    (getString(R.string.fatal_case) + " " + getString(R.string.no_data)).also {
                        country_deaths.text = it
                    }
                } else {
                    (getString(R.string.fatal_case) + " " + singleCountryInfo.totalDeathsText).also {
                        country_deaths.text = it
                    }
                }

                if (singleCountryInfo.newDeathsText == "") {
                    (getString(R.string.new_fatal_cases) + " " + getString(R.string.no_data)).also {
                        country_new_death.text = it
                    }
                } else {
                    (getString(R.string.new_fatal_cases) + " " + singleCountryInfo.newDeathsText).also {
                        country_new_death.text = it
                    }
                }

                if (singleCountryInfo.totalRecoveredText == "") {
                    (getString(R.string.recovered_cases) + " " + getString(R.string.no_data)).also {
                        country_recovered.text = it
                    }
                } else {
                    (getString(R.string.recovered_cases) + " " + singleCountryInfo.totalRecoveredText).also {
                        country_recovered.text = it
                    }
                }

                (getString(R.string.all_time_confirmed_cases) + " " + singleCountryInfo.totalCasesText).also {
                    country_confirmed.text = it
                }
                (getString(R.string.last_update) + " " + singleCountryInfo.lastUpdate).also {
                    last_update_data.text = it
                }

            }

        }
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            indicatorsContainer.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }
}