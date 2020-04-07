package com.OnlineEvent.umangburman.event.Models

import com.google.gson.annotations.SerializedName
import com.imagin.myapplication.Models.EventModels.Links
import com.imagin.myapplication.Models.EventModels.Meta

data class AgendaDaysModel(

        @SerializedName("data") val data: List<AgendaDaysData>,
        @SerializedName("links") val links: Links,
        @SerializedName("meta") val meta: Meta
)