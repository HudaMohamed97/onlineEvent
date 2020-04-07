package com.OnlineEvent.umangburman.event.DescriptionFragment

import androidx.lifecycle.MutableLiveData
import com.OnlineEvent.umangburman.event.Models.EventDescriptionModel
import com.OnlineEvent.umangburman.event.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LearnMoreRepository {
    fun getEventDescription(eventId: Int, accessToken: String): MutableLiveData<EventDescriptionModel> {
        val data = MutableLiveData<EventDescriptionModel>()
        Webservice.getInstance().api.getEventDescription(eventId, accessToken)
                .enqueue(object : Callback<EventDescriptionModel> {
                    override fun onResponse(
                            call: Call<EventDescriptionModel>,
                            response: Response<EventDescriptionModel>
                    ) {
                        if (response.isSuccessful) {
                            data.value = response.body()
                        } else {
                            data.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<EventDescriptionModel>, t: Throwable) {
                        data.value = null
                    }
                })
        return data

    }

}
