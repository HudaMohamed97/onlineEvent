package com.OnlineEvent.umangburman.event.SchduleFragment

import androidx.lifecycle.MutableLiveData
import com.OnlineEvent.umangburman.event.Models.SpeakerData
import com.OnlineEvent.umangburman.event.Models.SpeakerProfileModel
import com.OnlineEvent.umangburman.event.Models.SpeakerResponseModel
import com.OnlineEvent.umangburman.event.Models.SpeakersResponseModel
import com.OnlineEvent.umangburman.event.Models.scheduleModels.ScheduleResponseModel
import com.OnlineEvent.umangburman.event.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduleRepository {
    private lateinit var body: Map<String, String>

    fun getSchedules(currentPageNum: Int, accessToken: String): MutableLiveData<ScheduleResponseModel> {
        val userData = MutableLiveData<ScheduleResponseModel>()
        Webservice.getInstance().api.getMySchedule(currentPageNum, accessToken)
                .enqueue(object : Callback<ScheduleResponseModel> {
                    override fun onResponse(
                            call: Call<ScheduleResponseModel>, response: Response<ScheduleResponseModel>
                    ) {
                        if (response.isSuccessful) {
                            userData.value = response.body()
                        } else {
                            userData.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<ScheduleResponseModel>, t: Throwable) {
                        userData.value = null
                    }
                })
        return userData
    }

    fun getEvent(currentPageNum: Int, accessToken: String): MutableLiveData<ScheduleResponseModel> {
        val userData = MutableLiveData<ScheduleResponseModel>()
        Webservice.getInstance().api.getMyEvent(currentPageNum, accessToken)
                .enqueue(object : Callback<ScheduleResponseModel> {
                    override fun onResponse(
                            call: Call<ScheduleResponseModel>, response: Response<ScheduleResponseModel>
                    ) {
                        if (response.isSuccessful) {
                            userData.value = response.body()
                        } else {
                            userData.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<ScheduleResponseModel>, t: Throwable) {
                        userData.value = null
                    }
                })
        return userData
    }

    fun getEventSpeakers(eventId: Int, accessToken: String): MutableLiveData<SpeakerResponseModel> {
        val userData = MutableLiveData<SpeakerResponseModel>()
        Webservice.getInstance().api.getSpeakerEvent(eventId, accessToken)
                .enqueue(object : Callback<SpeakerResponseModel> {
                    override fun onResponse(
                            call: Call<SpeakerResponseModel>, response: Response<SpeakerResponseModel>
                    ) {
                        if (response.isSuccessful) {
                            userData.value = response.body()
                        } else {
                            userData.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<SpeakerResponseModel>, t: Throwable) {
                        userData.value = null
                    }
                })
        return userData
    }

    fun getSpeakers(accessToken: String): MutableLiveData<SpeakersResponseModel> {
        val userData = MutableLiveData<SpeakersResponseModel>()
        Webservice.getInstance().api.getSpeakers(accessToken)
                .enqueue(object : Callback<SpeakersResponseModel> {
                    override fun onResponse(
                            call: Call<SpeakersResponseModel>, response: Response<SpeakersResponseModel>
                    ) {
                        if (response.isSuccessful) {
                            userData.value = response.body()
                        } else {
                            userData.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<SpeakersResponseModel>, t: Throwable) {
                        userData.value = null
                    }
                })
        return userData
    }


    fun getSingleSpeakerSpeakers(speakerId: Int, accessToken: String): MutableLiveData<SpeakerProfileModel> {
        val userData = MutableLiveData<SpeakerProfileModel>()
        Webservice.getInstance().api.getSingelSpeakerEvent(speakerId, accessToken)
                .enqueue(object : Callback<SpeakerProfileModel> {
                    override fun onResponse(
                            call: Call<SpeakerProfileModel>, response: Response<SpeakerProfileModel>
                    ) {
                        if (response.isSuccessful) {
                            userData.value = response.body()
                        } else {
                            userData.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<SpeakerProfileModel>, t: Throwable) {
                        userData.value = null
                    }
                })
        return userData
    }


    fun getSchedulesFiltered(month: String, speaker: Int, topic: String, currentPageNum: Int, accessToken: String): MutableLiveData<ScheduleResponseModel> {
        filtersCondition(month, speaker, topic)
        val userData = MutableLiveData<ScheduleResponseModel>()
        Webservice.getInstance().api.getMyScheduleByFilter(body, currentPageNum, accessToken)
                .enqueue(object : Callback<ScheduleResponseModel> {
                    override fun onResponse(
                            call: Call<ScheduleResponseModel>, response: Response<ScheduleResponseModel>
                    ) {
                        if (response.isSuccessful) {
                            userData.value = response.body()
                        } else {
                            userData.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<ScheduleResponseModel>, t: Throwable) {
                        userData.value = null
                    }
                })
        return userData
    }

    private fun filtersCondition(month: String, speaker: Int, topic: String) {
        if (month != "" && speaker != -1 && topic != "") {
            body = mapOf(
                    "month" to month,
                    "topic" to topic,
                    "speaker_id" to speaker.toString()
            )
        } else if (month != "" && speaker == -1 && topic == "") {
            body = mapOf(
                    "month" to month
            )
        } else if (month != "" && speaker != -1 && topic == "") {
            body = mapOf(
                    "month" to month,
                    "speaker_id" to speaker.toString()
            )
        } else if (month != "" && speaker == -1 && topic != "") {
            body = mapOf(
                    "month" to month,
                    "topic" to topic
            )
        } else if (month == "" && speaker != -1 && topic == "") {
            body = mapOf(
                    "speaker_id" to month
            )
        } else if (month == "" && speaker != -1 && topic != "") {
            body = mapOf(
                    "speaker_id" to month,
                    "topic" to topic
            )
        } else if (month == "" && speaker == -1 && topic != "") {
            body = mapOf(
                    "topic" to month
            )
        }

    }
}