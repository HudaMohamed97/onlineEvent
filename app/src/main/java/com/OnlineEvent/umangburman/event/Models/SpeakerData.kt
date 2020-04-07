package com.OnlineEvent.umangburman.event.Models

import com.google.gson.annotations.SerializedName

data class SpeakerData(@SerializedName("id") val id: Int,
                       @SerializedName("name") val name: String,
                       @SerializedName("email") val email: String,
                       @SerializedName("phone") val phone: String,
                       @SerializedName("bio") val bio: String,
                       @SerializedName("type") val type: Int,
                       @SerializedName("specialty") val specialty: String,
                       @SerializedName("address") val address: String,
                       @SerializedName("city") val city: String,
                       @SerializedName("company") val company: Company,
                       @SerializedName("country") val country: Country,
                       @SerializedName("photo") val photo: String)

