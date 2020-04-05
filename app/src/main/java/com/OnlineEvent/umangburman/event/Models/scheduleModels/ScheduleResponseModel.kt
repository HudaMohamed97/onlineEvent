package com.OnlineEvent.umangburman.event.Models.scheduleModels

import com.google.gson.annotations.SerializedName
import com.imagin.myapplication.Models.EventModels.Links
import com.imagin.myapplication.Models.EventModels.Meta

data class ScheduleResponseModel(
        @SerializedName("data") val data: List<ScheduleResponse>,
        @SerializedName("links") val links: Links,
        @SerializedName("meta") val meta: Meta
)