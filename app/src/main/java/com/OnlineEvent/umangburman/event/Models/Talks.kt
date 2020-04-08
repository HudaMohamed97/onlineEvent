package com.OnlineEvent.umangburman.event.Models

import com.google.gson.annotations.SerializedName

data class Talks(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("desc") val desc: String,
        @SerializedName("location") val location: String,
        @SerializedName("time_from") val time_from: String,
        @SerializedName("time_to") val time_to: String,
        @SerializedName("speakers") val speakers: List<SpeakerData>
)