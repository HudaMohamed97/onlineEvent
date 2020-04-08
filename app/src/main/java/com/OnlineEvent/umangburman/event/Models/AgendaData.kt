package com.OnlineEvent.umangburman.event.Models

import com.google.gson.annotations.SerializedName

class AgendaData(@SerializedName("id") val id : Int,
                 @SerializedName("date") val date : String,
                 @SerializedName("photo") val photo : String,
                 @SerializedName("talks") val talks : List<Talks>)
