package com.OnlineEvent.umangburman.event.Models

import com.google.gson.annotations.SerializedName

data class LoginRequestModel(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

