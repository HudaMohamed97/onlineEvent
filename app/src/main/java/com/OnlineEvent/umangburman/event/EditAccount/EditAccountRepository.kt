package com.OnlineEvent.umangburman.event.EditAccount

import androidx.lifecycle.MutableLiveData
import com.OnlineEvent.umangburman.event.NetworkLayer.Webservice
import com.example.myapplication.Models.AccountModelData
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class EditAccountRepository {
    fun updateData(
            file: String,
            email: String,
            name: String, bio: String,
            accessToken: String
    ): MutableLiveData<AccountModelData> {
        val updateData = MutableLiveData<AccountModelData>()
        val email = RequestBody.create(MediaType.parse("multipart/form-data"), email)
        val name = RequestBody.create(MediaType.parse("multipart/form-data"), name)
        val bio = RequestBody.create(MediaType.parse("multipart/form-data"), bio)

        var fileToUpload: MultipartBody.Part? = null
        try {
            val file = File(file)
            fileToUpload = if (file.exists()) {
                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                MultipartBody.Part.createFormData("photo", file.name, requestFile)
            } else {
                val attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "")
                MultipartBody.Part.createFormData("photo", "", attachmentEmpty)
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

        Webservice.getInstance().api.updateAccount(email, name, bio, fileToUpload!!, accessToken)
                .enqueue(object : Callback<AccountModelData> {
                    override fun onResponse(
                            call: Call<AccountModelData>,
                            response: Response<AccountModelData>
                    ) {
                        if (response.isSuccessful) {
                            response.raw()
                            updateData.value = response.body()
                        } else {
                            updateData.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<AccountModelData>, t: Throwable) {
                        updateData.value = null
                    }
                })
        return updateData
    }

    fun updatePassword(
            currentPass: String,
            newPass: String,
            accessToken: String
    ): MutableLiveData<AccountModelData> {
        val updateData = MutableLiveData<AccountModelData>()
        val body = mapOf(
                "password" to newPass,
                "current_password" to currentPass
        )
        Webservice.getInstance().api.updatePassword(body, accessToken)
                .enqueue(object : Callback<AccountModelData> {
                    override fun onResponse(
                            call: Call<AccountModelData>,
                            response: Response<AccountModelData>
                    ) {
                        if (response.isSuccessful) {
                            response.raw()
                            updateData.value = response.body()
                        } else {
                            updateData.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<AccountModelData>, t: Throwable) {
                        updateData.value = null
                    }
                })
        return updateData
    }
}
