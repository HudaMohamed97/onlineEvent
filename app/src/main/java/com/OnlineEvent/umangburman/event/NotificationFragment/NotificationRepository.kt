package com.OnlineEvent.umangburman.event.NotificationFragment

import androidx.lifecycle.MutableLiveData
import com.OnlineEvent.umangburman.event.NetworkLayer.Webservice
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationRepository {
    fun getNotification(auth: String): MutableLiveData<ResponseBody> {
        val data = MutableLiveData<ResponseBody>()
        Webservice.getInstance().api.getNotification(auth)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            data.value = response.body()
                        } else {
                            data.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        data.value = null
                    }
                })
        return data

    }

}
