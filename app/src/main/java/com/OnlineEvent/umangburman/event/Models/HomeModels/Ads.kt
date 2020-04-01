package com.OnlineEvent.umangburman.event.Models.HomeModels

import com.google.gson.annotations.SerializedName


data class Ads(
        @SerializedName("is_image") val is_image: Boolean,
        @SerializedName("video_url") val video_url: String?=null,
        @SerializedName("photo") val photo: String?=null
)