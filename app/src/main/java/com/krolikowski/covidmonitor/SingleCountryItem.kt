package com.krolikowski.covidmonitor


import com.google.gson.annotations.SerializedName

data class SingleCountryItem(
    @SerializedName("country")
    val country: String,
    @SerializedName("provinces")
    val provinces: List<Province>,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("date")
    val date: String
)