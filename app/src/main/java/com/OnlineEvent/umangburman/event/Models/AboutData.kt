package com.OnlineEvent.umangburman.event.Models

import com.google.gson.annotations.SerializedName

data class AboutData
(
        @SerializedName("value") val value: String,
        @SerializedName("photo") val photo: String
)
