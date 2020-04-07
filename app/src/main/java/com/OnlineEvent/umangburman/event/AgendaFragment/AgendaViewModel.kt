package com.OnlineEvent.umangburman.event.AgendaFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.OnlineEvent.umangburman.event.Models.AboutModel
import com.OnlineEvent.umangburman.event.Models.AgendaDaysModel
import com.OnlineEvent.umangburman.event.Models.HomeModels.HomeResponseModel
import com.example.myapplication.Models.AccountModelData


class AgendaViewModel : ViewModel() {
    private var repositoryHelper: AgendaRepository = AgendaRepository()
    private lateinit var mutableLiveData: MutableLiveData<AgendaDaysModel>
    private lateinit var homeMutableLiveData: MutableLiveData<HomeResponseModel>
    private lateinit var accountMutableLiveData: MutableLiveData<AccountModelData>


    fun getAgendaDays(eventdId:Int,auth: String) {
        mutableLiveData = repositoryHelper.getAgendaDays(eventdId,auth)

    }

    fun getAgendaDaysData(): MutableLiveData<AgendaDaysModel> {
        return mutableLiveData
    }

   /* fun getHomeData(auth: String) {
        homeMutableLiveData = repositoryHelper.getHome(auth)

    }

    fun homeData(): MutableLiveData<HomeResponseModel> {
        return homeMutableLiveData
    }*/


}







