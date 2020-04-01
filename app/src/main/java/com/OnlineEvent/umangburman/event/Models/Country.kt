package com.OnlineEvent.umangburman.event.Models

import com.google.gson.annotations.SerializedName

data class Country (

		@SerializedName("id") val id : Int,
		@SerializedName("name") val name : String
)