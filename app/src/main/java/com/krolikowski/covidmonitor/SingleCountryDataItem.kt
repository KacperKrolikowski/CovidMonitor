package com.krolikowski.covidmonitor


import com.google.gson.annotations.SerializedName

data class SingleCountryDataItem(
    @SerializedName("confirmed")
    val confirmed: Int,
    @SerializedName("country")
    val country: String,
    @SerializedName("critical")
    val critical: Int,
    @SerializedName("deaths")
    val deaths: Int,
    @SerializedName("lastChange")
    val lastChange: String,
    @SerializedName("lastUpdate")
    val lastUpdate: String,
    @SerializedName("latitude")
    val latitude: Int,
    @SerializedName("longitude")
    val longitude: Int,
    @SerializedName("recovered")
    val recovered: Int
)