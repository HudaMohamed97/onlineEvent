package com.OnlineEvent.umangburman.event.EditAccount

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Models.AccountModelData


class EditViewModel : ViewModel() {
    private var repositoryHelper: EditAccountRepository = EditAccountRepository()
    private lateinit var mutableLiveData: MutableLiveData<AccountModelData>


    fun updateAccount(file: String, email: String, name: String, bio: String, auth: String) {
        mutableLiveData = repositoryHelper.updateData(file, email, name, bio, auth)

    }

    fun getStatues(): MutableLiveData<AccountModelData> {
        return mutableLiveData
    }


}







