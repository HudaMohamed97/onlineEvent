package com.OnlineEvent.umangburman.event.SchduleFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.OnlineEvent.umangburman.event.Models.SpeakersResponseModel
import com.OnlineEvent.umangburman.event.Models.SubmitModel
import com.OnlineEvent.umangburman.event.Models.scheduleModels.ScheduleResponseModel

class ScheduleViewModel : ViewModel() {
    private var repositoryHelper: ScheduleRepository = ScheduleRepository()
    private lateinit var mutableLiveData: MutableLiveData<ScheduleResponseModel>
    private lateinit var registerMutableLiveData: MutableLiveData<SubmitModel>
    private lateinit var filteredLiveData: MutableLiveData<ScheduleResponseModel>
    private lateinit var speakerLiveData: MutableLiveData<SpeakersResponseModel>


    fun getSchedulesData(currentPageNum: Int, accessToken: String) {
        mutableLiveData = repositoryHelper.getSchedules(currentPageNum, accessToken)

    }

    fun getData(): MutableLiveData<ScheduleResponseModel> {
        return mutableLiveData
    }

    fun registerToEvent(eventId: Int, accessToken: String) {
        registerMutableLiveData = repositoryHelper.registerToEvent(eventId, accessToken)

    }

    fun getRegisterData(): MutableLiveData<SubmitModel> {
        return registerMutableLiveData
    }

    fun getSchedulesByFilter(month: String, speaker: Int, topic: String, currentPageNum: Int, accessToken: String) {
        filteredLiveData = repositoryHelper.getSchedulesFiltered(month, speaker, topic, currentPageNum, accessToken)

    }

    fun getFilteredData(): MutableLiveData<ScheduleResponseModel> {
        return filteredLiveData
    }

    fun getSpeakers(accessToken: String) {
        speakerLiveData = repositoryHelper.getSpeakers(accessToken)

    }

    fun getSpeakerData(): MutableLiveData<SpeakersResponseModel> {
        return speakerLiveData
    }


}
