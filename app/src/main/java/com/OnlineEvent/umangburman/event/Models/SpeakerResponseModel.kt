package com.OnlineEvent.umangburman.event.Models

import com.google.gson.annotations.SerializedName


data class SpeakerResponseModel(@SerializedName("data") val data: List<SpeakerData>
)
