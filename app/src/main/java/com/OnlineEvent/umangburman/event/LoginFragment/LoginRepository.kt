package com.OnlineEvent.umangburman.event.LoginFragment

import androidx.lifecycle.MutableLiveData
import com.OnlineEvent.umangburman.event.Models.LoginRequestModel
import com.OnlineEvent.umangburman.event.Models.SubmitModel
import com.OnlineEvent.umangburman.event.NetworkLayer.Webservice
import com.example.myapplication.Models.ResponseModelData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginRepository {
    companion object {
        const val ERROR_CODE = 401   //this for Wrong Password
    }

    fun login(email: String, password: String): MutableLiveData<ResponseModelData> {
        val userData = MutableLiveData<ResponseModelData>()
        val body = LoginRequestModel(email.trim(), password)
        Webservice.getInstance().api.login(body).enqueue(object : Callback<ResponseModelData> {
            override fun onResponse(
                    call: Call<ResponseModelData>,
                    response: Response<ResponseModelData>
            ) {
                if (response.isSuccessful) {
                    userData.value = response.body()
                } else {
                    when {
                        response.code() == ERROR_CODE -> {
                            var jObjError: JSONObject? = null
                            try {
                                jObjError = JSONObject(response.errorBody()?.string())
                            } catch (e: Exception) {
                                e.message
                            }
                            val dummyResponse =
                                    ResponseModelData(
                                            "",
                                            jObjError!!["title"].toString(),
                                            "",
                                            null
                                    )
                            userData.value = dummyResponse
                        }
                        response.code() == 422 -> {
                            val dummyResponse =
                                    ResponseModelData(
                                            "",
                                            "Password must be at leaast 6 length",
                                            "",
                                            null
                                    )
                            userData.value = dummyResponse
                        }
                        else -> {
                            val dummyResponse =
                                    ResponseModelData("", response.message(), "", null)
                            userData.value = dummyResponse
                        }
                    }

                }

            }

            override fun onFailure(call: Call<ResponseModelData>, t: Throwable) {
                userData.value = null
            }
        })

        return userData

    }

    fun resetPassword(email: String): MutableLiveData<SubmitModel> {
        val userData = MutableLiveData<SubmitModel>()
        val body = mapOf(
                "email" to email.trim()

        )
        Webservice.getInstance().api.resetPassword(body).enqueue(object : Callback<SubmitModel> {
            override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
            ) {
                if (response.isSuccessful) {
                    userData.value = response.body()
                } else {
                    userData.value = response.body()
                }

            }

            override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                userData.value = null
            }
        })

        return userData

    }


}



