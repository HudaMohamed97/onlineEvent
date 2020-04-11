package com.OnlineEvent.umangburman.event.NetworkLayer

import com.OnlineEvent.umangburman.event.Models.*
import com.OnlineEvent.umangburman.event.Models.HomeModels.HomeResponseModel
import com.OnlineEvent.umangburman.event.Models.scheduleModels.ScheduleResponseModel
import com.example.myapplication.Models.AccountModelData
import com.example.myapplication.Models.ResponseModelData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiServices {

    @POST("auth/login")
    fun login(@Body loginRequestModel: LoginRequestModel): Call<ResponseModelData>

    @POST("auth/login")
    fun resetPassword(@Body email: Map<String, String>): Call<SubmitModel>

    @GET("about")
    fun getAboutData(@Header("Authorization") authHeader: String): Call<AboutModel>

    @GET("events/{event}")
    fun getEventDescription(@Path("event") event: Int, @Header("Authorization") authHeader: String): Call<EventDescriptionModel>

    @GET("home")
    fun getHomeData(@Header("Authorization") authHeader: String): Call<HomeResponseModel>

    @GET("my-schedule")
    fun getMySchedule(@Query("page") page: Int, @Header("Authorization") authHeader: String): Call<ScheduleResponseModel>

    @GET("events")
    fun getMyEvent(@Query("page") page: Int, @Header("Authorization") authHeader: String): Call<ScheduleResponseModel>

    @GET("events/{event}/days")
    fun getAagendaDays(@Path("event") event: Int, @Header("Authorization") authHeader: String): Call<AgendaDaysModel>

    @GET("days/{day}")
    fun getAgenda(@Path("day") day: Int, @Header("Authorization") authHeader: String): Call<AgendaModelResponse>

    @GET("events/{event}/speakers")
    fun getSpeakerEvent(@Path("event") event: Int, @Header("Authorization") authHeader: String): Call<SpeakerResponseModel>

    @GET("speakers/{speaker}")
    fun getSingelSpeakerEvent(@Path("speaker") event: Int, @Header("Authorization") authHeader: String): Call<SpeakerProfileModel>

    @GET("speakers")
    fun getSpeakers(@Header("Authorization") authHeader: String): Call<SpeakersResponseModel>

    @GET("my-schedule")
    fun getMyScheduleByFilter(@QueryMap map: Map<String, String>, @Query("page") page: Int, @Header("Authorization") authHeader: String): Call<ScheduleResponseModel>

    @GET("account/me")
    fun getMyAccount(@Header("Authorization") authHeader: String): Call<AccountModelData>

    @Multipart
    @POST("account/update")
    fun updateAccount(
            @Part("email") email: RequestBody, @Part("name") name: RequestBody
            , @Part("bio") bio: RequestBody, @Part image: MultipartBody.Part,
            @Header("Authorization") authHeader: String
    ): Call<AccountModelData>


}