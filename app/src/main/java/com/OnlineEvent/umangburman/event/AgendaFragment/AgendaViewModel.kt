package com.OnlineEvent.umangburman.event.AgendaFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.OnlineEvent.umangburman.event.Models.AboutModel
import com.OnlineEvent.umangburman.event.Models.AgendaDaysModel
import com.OnlineEvent.umangburman.event.Models.AgendaModelResponse
import com.OnlineEvent.umangburman.event.Models.HomeModels.HomeResponseModel
import com.example.myapplication.Models.AccountModelData


class AgendaViewModel : ViewModel() {
    private var repositoryHelper: AgendaRepository = AgendaRepository()
    private lateinit var mutableLiveData: MutableLiveData<AgendaDaysModel>
    private lateinit var agendaMutableLiveData: MutableLiveData<AgendaModelResponse>


    fun getAgendaDays(eventdId:Int,auth: String) {
        mutableLiveData = repositoryHelper.getAgendaDays(eventdId,auth)

    }

    fun getAgendaDaysData(): MutableLiveData<AgendaDaysModel> {
        return mutableLiveData
    }
    fun getAgenda(day: Int, auth: String) {
        agendaMutableLiveData = repositoryHelper.getAgenda(day, auth)

    }

    fun getAgendaData(): MutableLiveData<AgendaModelResponse> {
        return agendaMutableLiveData
    }



}







