package com.OnlineEvent.umangburman.event.Models

import com.google.gson.annotations.SerializedName

data class DescriptionData(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("status") val status: Int,
        @SerializedName("remaining_time") val remaining_time: String,
        @SerializedName("description") val description: String,
        @SerializedName("start_date") val start_date: String,
        @SerializedName("end_date") val end_date: String,
        @SerializedName("city") val city: String,
        @SerializedName("address") val address: String,
        @SerializedName("lat") val lat: Int,
        @SerializedName("lng") val lng: Int,
        @SerializedName("photo") val photo: String)