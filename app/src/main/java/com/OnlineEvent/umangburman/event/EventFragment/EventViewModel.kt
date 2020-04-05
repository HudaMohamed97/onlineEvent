package com.OnlineEvent.umangburman.event.EventFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.OnlineEvent.umangburman.event.Models.scheduleModels.ScheduleResponseModel
import com.OnlineEvent.umangburman.event.SchduleFragment.ScheduleRepository

class EventViewModel : ViewModel() {
    private var repositoryHelper: ScheduleRepository = ScheduleRepository()
    private lateinit var mutableLiveData: MutableLiveData<ScheduleResponseModel>

    fun getEventData(currentPageNum: Int, accessToken: String) {
        mutableLiveData = repositoryHelper.getEvent(currentPageNum, accessToken)

    }

    fun getData(): MutableLiveData<ScheduleResponseModel> {
        return mutableLiveData
    }


}
