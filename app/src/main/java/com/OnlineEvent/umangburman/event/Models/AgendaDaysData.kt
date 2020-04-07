package com.OnlineEvent.umangburman.event.Models

import com.google.gson.annotations.SerializedName

class AgendaDaysData(@SerializedName("id") val id: Int,
                     @SerializedName("date") val agenda_date: String,
                     @SerializedName("photo") val photo: String
)
