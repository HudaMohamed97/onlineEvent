package com.OnlineEvent.umangburman.event.Models

import com.google.gson.annotations.SerializedName

data class Company(

        @SerializedName("name") val name: String,
        @SerializedName("website") val website: String,
        @SerializedName("intro_video") val intro_video: String,
        @SerializedName("description") val description: String,
        @SerializedName("logo") val logo: String
)