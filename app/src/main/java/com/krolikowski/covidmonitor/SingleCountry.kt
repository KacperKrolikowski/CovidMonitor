package com.krolikowski.covidmonitor


import com.google.gson.annotations.SerializedName

data class SingleCountry(
    @SerializedName("Active Cases_text")
    val activeCasesText: String,
    @SerializedName("Country_text")
    val countryText: String,
    @SerializedName("Last Update")
    val lastUpdate: String,
    @SerializedName("New Cases_text")
    val newCasesText: String,
    @SerializedName("New Deaths_text")
    val newDeathsText: String,
    @SerializedName("Total Cases_text")
    val totalCasesText: String,
    @SerializedName("Total Deaths_text")
    val totalDeathsText: String,
    @SerializedName("Total Recovered_text")
    val totalRecoveredText: String
)