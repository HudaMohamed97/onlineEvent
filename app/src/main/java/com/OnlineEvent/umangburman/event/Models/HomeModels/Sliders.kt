package com.OnlineEvent.umangburman.event.Models.HomeModels

import com.google.gson.annotations.SerializedName


data class Sliders (
		@SerializedName("title") val title : String,
		@SerializedName("is_image") val is_image : Boolean,
		@SerializedName("button") val button : Button,
		@SerializedName("photo") val photo : String,
		@SerializedName("video_url") val video_url : String
)