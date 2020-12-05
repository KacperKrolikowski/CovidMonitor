package com.krolikowski.covidmonitor

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_country_selector.*

class CountrySelectorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_selector)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        var selectedCountry = "World"

        country_picker.minValue = 0
        country_picker.maxValue = 33

        val pickerValues = arrayOf("World", "Argentina", "Bangladesh", "Brazil", "Canada", "China", "Columbia", "Denmark", "Egypt", "Ethiopia", "France", "Germany", "Greece", "India", "Indonesia", "Iran", "Italy", "Japan", "Kenya", "Mexico", "Nigeria", "Norway", "Pakistan", "Philippines", "Poland", "Russia", "Slovakia", "Slovenia", "Spain", "Turkey", "Uk", "Ukraine", "Usa", "Vietnam")

        country_picker.displayedValues = pickerValues

        country_picker.setOnValueChangedListener { picker, oldVal, newVal -> selectedCountry =
            pickerValues[newVal]
        }

        arrow_next.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("country", selectedCountry)
            startActivity(intent)
            finish()
        }
    }
}