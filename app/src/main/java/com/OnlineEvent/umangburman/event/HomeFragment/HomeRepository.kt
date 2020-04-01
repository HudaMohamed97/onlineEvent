package com.OnlineEvent.umangburman.event.HomeFragment

import androidx.lifecycle.MutableLiveData
import com.OnlineEvent.umangburman.event.Models.AboutModel
import com.OnlineEvent.umangburman.event.Models.HomeModels.HomeResponseModel
import com.OnlineEvent.umangburman.event.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {
    fun getAbout(auth: String): MutableLiveData<AboutModel> {
        val data = MutableLiveData<AboutModel>()
        Webservice.getInstance().api.getAboutData(auth)
                .enqueue(object : Callback<AboutModel> {
                    override fun onResponse(
                            call: Call<AboutModel>,
                            response: Response<AboutModel>
                    ) {
                        if (response.isSuccessful) {
                            data.value = response.body()
                        } else {
                            data.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<AboutModel>, t: Throwable) {
                        data.value = null
                    }
                })
        return data

    }

    fun getHome(auth: String): MutableLiveData<HomeResponseModel> {
        val data = MutableLiveData<HomeResponseModel>()
        Webservice.getInstance().api.getHomeData(auth)
                .enqueue(object : Callback<HomeResponseModel> {
                    override fun onResponse(
                            call: Call<HomeResponseModel>,
                            response: Response<HomeResponseModel>
                    ) {
                        if (response.isSuccessful) {
                            data.value = response.body()
                        } else {
                            data.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<HomeResponseModel>, t: Throwable) {
                        data.value = null
                    }
                })
        return data
    }

}
