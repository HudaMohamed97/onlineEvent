package com.example.myapplication.Models

import com.google.gson.annotations.SerializedName

data class AccountModelData(
        @SerializedName("data") val account: Account
)

