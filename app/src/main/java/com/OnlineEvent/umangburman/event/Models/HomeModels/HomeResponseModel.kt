package com.OnlineEvent.umangburman.event.Models.HomeModels

import com.google.gson.annotations.SerializedName

data class HomeResponseModel(
        @SerializedName("sliders") val sliders: List<Sliders>,
        @SerializedName("event") val event: Event,
        @SerializedName("video") val video: String,
        @SerializedName("ads") val ads: List<Ads>
)