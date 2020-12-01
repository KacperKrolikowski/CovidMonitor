package com.krolikowski.covidmonitor


import com.google.gson.annotations.SerializedName

data class Province(
    @SerializedName("active")
    val active: Int,
    @SerializedName("confirmed")
    val confirmed: Int,
    @SerializedName("deaths")
    val deaths: Int,
    @SerializedName("province")
    val province: String,
    @SerializedName("recovered")
    val recovered: Int
)