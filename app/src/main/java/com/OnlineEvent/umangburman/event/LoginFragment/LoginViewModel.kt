package com.OnlineEvent.umangburman.event.LoginFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.OnlineEvent.umangburman.event.LoginFragment.LoginRepository
import com.OnlineEvent.umangburman.event.Models.SubmitModel
import com.example.catapplication.utilies.Validation
import com.example.myapplication.Models.ResponseModelData


class LoginViewModel : ViewModel() {
    private var repositoryHelper: LoginRepository = LoginRepository()
    private lateinit var mutableLiveData: MutableLiveData<ResponseModelData>
    private lateinit var resetLiveData: MutableLiveData<SubmitModel>

    fun validateLoginInfo(
            emailEt: String,
            passwordEt: String
    ): Boolean {
        val isEmailValid = Validation.validateEmail(emailEt)
        val isPasswordValid = Validation.validate(passwordEt)
        return !(!isEmailValid || !isPasswordValid)
    }


    fun login(emailEt: String, passwordEt: String) {
        mutableLiveData = repositoryHelper.login(emailEt, passwordEt)

    }

    fun getData(): MutableLiveData<ResponseModelData> {
        return mutableLiveData
    }

    fun resetPassword(emailEt: String) {
        resetLiveData = repositoryHelper.resetPassword(emailEt)

    }

    fun getResetData(): MutableLiveData<SubmitModel> {
        return resetLiveData
    }

}







