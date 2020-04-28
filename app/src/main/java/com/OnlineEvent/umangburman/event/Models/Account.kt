package com.example.myapplication.Models

import com.OnlineEvent.umangburman.event.Models.Company
import com.OnlineEvent.umangburman.event.Models.Country
import com.google.gson.annotations.SerializedName


data class Account(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("email") val email: String,
        @SerializedName("phone") val phone: String,
        @SerializedName("bio") val bio: String,
        @SerializedName("type") val type: Int,
        @SerializedName("company") val company: Company,
        @SerializedName("country") val country: Country,
        @SerializedName("photo") val photo: String
)
