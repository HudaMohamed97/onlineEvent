package com.OnlineEvent.umangburman.event.Models.HomeModels

import com.google.gson.annotations.SerializedName

data class Event(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("status") val status: String,
        @SerializedName("description") val description: String,
        @SerializedName("remaining_time") val remaining_time: String,
        @SerializedName("start_date") val start_date: Int,
        @SerializedName("end_date") val end_date: Int,
        @SerializedName("city") val city: String,
        @SerializedName("address") val address: String,
        @SerializedName("lat") val lat: Int,
        @SerializedName("lng") val lng: Int,
        @SerializedName("photo") val photo: String
)