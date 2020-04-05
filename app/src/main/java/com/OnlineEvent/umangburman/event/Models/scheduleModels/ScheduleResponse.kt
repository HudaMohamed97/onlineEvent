package com.OnlineEvent.umangburman.event.Models.scheduleModels

import com.google.gson.annotations.SerializedName

data class ScheduleResponse(@SerializedName("id") val id: Int,
                            @SerializedName("name") val name: String,
                            @SerializedName("status") val status: Int,
                            @SerializedName("remaining_time") val remaining_time: String,
                            @SerializedName("description") val description: String,
                            @SerializedName("start_date") val start_date: String,
                            @SerializedName("end_date") val end_date: String,
                            @SerializedName("city") val city: String,
                            @SerializedName("address") val address: String,
                            @SerializedName("lat") val lat: String,
                            @SerializedName("lng") val lng: String,
                            @SerializedName("photo") val photo: String)



