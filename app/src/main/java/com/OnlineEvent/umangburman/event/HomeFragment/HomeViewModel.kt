package com.OnlineEvent.umangburman.event.HomeFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.OnlineEvent.umangburman.event.Models.AboutModel
import com.OnlineEvent.umangburman.event.Models.HomeModels.HomeResponseModel
import com.example.myapplication.Models.AccountModelData


class HomeViewModel : ViewModel() {
    private var repositoryHelper: HomeRepository = HomeRepository()
    private lateinit var mutableLiveData: MutableLiveData<AboutModel>
    private lateinit var homeMutableLiveData: MutableLiveData<HomeResponseModel>
    private lateinit var accountMutableLiveData: MutableLiveData<AccountModelData>


    fun getAbout(auth: String) {
        mutableLiveData = repositoryHelper.getAbout(auth)

    }

    fun getAboutData(): MutableLiveData<AboutModel> {
        return mutableLiveData
    }

    fun getHomeData(auth: String) {
        homeMutableLiveData = repositoryHelper.getHome(auth)

    }

    fun homeData(): MutableLiveData<HomeResponseModel> {
        return homeMutableLiveData
    }

    fun getMyAccountData(auth: String) {
        accountMutableLiveData = repositoryHelper.getMyAccount(auth)

    }

    fun AaccountData(): MutableLiveData<AccountModelData> {
        return accountMutableLiveData
    }

}







