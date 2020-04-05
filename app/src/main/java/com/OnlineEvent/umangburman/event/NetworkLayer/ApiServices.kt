package com.OnlineEvent.umangburman.event.NetworkLayer

import com.OnlineEvent.umangburman.event.Models.AboutModel
import com.OnlineEvent.umangburman.event.Models.HomeModels.HomeResponseModel
import com.OnlineEvent.umangburman.event.Models.LoginRequestModel
import com.OnlineEvent.umangburman.event.Models.SchduleResponseData
import com.OnlineEvent.umangburman.event.Models.scheduleModels.ScheduleResponseModel
import com.example.myapplication.Models.ResponseModelData
import retrofit2.Call
import retrofit2.http.*


interface ApiServices {

    @POST("auth/login")
    fun login(@Body loginRequestModel: LoginRequestModel): Call<ResponseModelData>

    @GET("about")
    fun getAboutData(@Header("Authorization") authHeader: String): Call<AboutModel>

    @GET("home")
    fun getHomeData(@Header("Authorization") authHeader: String): Call<HomeResponseModel>

    @GET("my-schedule")
    fun getMySchedule(@Query("page") page: Int, @Header("Authorization") authHeader: String): Call<ScheduleResponseModel>

    @GET("my-schedule")
    fun getMyScheduleByFilter(@QueryMap map: Map<String, String>, @Query("page") page: Int, @Header("Authorization") authHeader: String): Call<ScheduleResponseModel>


}