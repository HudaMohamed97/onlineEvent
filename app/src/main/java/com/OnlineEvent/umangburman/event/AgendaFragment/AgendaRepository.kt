package com.OnlineEvent.umangburman.event.AgendaFragment

import androidx.lifecycle.MutableLiveData
import com.OnlineEvent.umangburman.event.Models.AgendaDaysModel
import com.OnlineEvent.umangburman.event.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgendaRepository {
    fun getAgendaDays(eventId: Int, auth: String): MutableLiveData<AgendaDaysModel> {
        val data = MutableLiveData<AgendaDaysModel>()
        Webservice.getInstance().api.getAagendaDays(eventId,auth)
                .enqueue(object : Callback<AgendaDaysModel> {
                    override fun onResponse(
                            call: Call<AgendaDaysModel>,
                            response: Response<AgendaDaysModel>
                    ) {
                        if (response.isSuccessful) {
                            data.value = response.body()
                        } else {
                            data.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<AgendaDaysModel>, t: Throwable) {
                        data.value = null
                    }
                })
        return data
    }

}
