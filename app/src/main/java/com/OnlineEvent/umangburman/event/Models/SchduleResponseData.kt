package com.OnlineEvent.umangburman.event.Models

import com.google.gson.annotations.SerializedName

data class SchduleResponseData(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("status") val status: String,
        @SerializedName("is_referral") val is_referral: Boolean,
        @SerializedName("created_at") val created_at: String
)
