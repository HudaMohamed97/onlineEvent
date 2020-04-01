package com.OnlineEvent.umangburman.event.NetworkLayer

import com.OnlineEvent.umangburman.event.Models.AboutModel
import com.OnlineEvent.umangburman.event.Models.HomeModels.HomeResponseModel
import com.OnlineEvent.umangburman.event.Models.LoginRequestModel
import com.example.myapplication.Models.ResponseModelData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiServices {

    @POST("auth/login")
    fun login(@Body loginRequestModel: LoginRequestModel): Call<ResponseModelData>

    @GET("about")
    fun getAboutData(@Header("Authorization") authHeader: String): Call<AboutModel>

    @GET("home")
    fun getHomeData(@Header("Authorization") authHeader: String): Call<HomeResponseModel>


}