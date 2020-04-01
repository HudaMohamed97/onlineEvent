package com.OnlineEvent.umangburman.event.Models.HomeModels

import com.google.gson.annotations.SerializedName

data class Button(
        @SerializedName("text") val text: String,
        @SerializedName("color") val color: String,
        @SerializedName("url") val url: String
)