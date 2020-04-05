package com.OnlineEvent.umangburman.event.SchduleFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.OnlineEvent.umangburman.event.Models.scheduleModels.ScheduleResponseModel

class ScheduleViewModel : ViewModel() {
    private var repositoryHelper: ScheduleRepository = ScheduleRepository()
    private lateinit var mutableLiveData: MutableLiveData<ScheduleResponseModel>
    private lateinit var filteredLiveData: MutableLiveData<ScheduleResponseModel>

    fun getSchedulesData(currentPageNum: Int, accessToken: String) {
        mutableLiveData = repositoryHelper.getSchedules(currentPageNum, accessToken)

    }

    fun getData(): MutableLiveData<ScheduleResponseModel> {
        return mutableLiveData
    }

    fun getSchedulesByFilter(month: String, speaker: Int, topic: String, currentPageNum: Int, accessToken: String) {
        filteredLiveData = repositoryHelper.getSchedulesFiltered(month, speaker, topic, currentPageNum, accessToken)

    }

    fun getFilteredData(): MutableLiveData<ScheduleResponseModel> {
        return filteredLiveData
    }

}
