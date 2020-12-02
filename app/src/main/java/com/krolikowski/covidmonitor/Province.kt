package com.krolikowski.covidmonitor


import com.google.gson.annotations.SerializedName

data class Province(
    @SerializedName("province")
    val province: String,
    @SerializedName("confirmed")
    val confirmed: Int,
    @SerializedName("recovered")
    val recovered: Int,
    @SerializedName("deaths")
    val deaths: Int,
    @SerializedName("active")
    val active: Int
)