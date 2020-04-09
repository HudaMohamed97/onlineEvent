package com.OnlineEvent.umangburman.event.EventFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.OnlineEvent.umangburman.event.Models.SpeakerData
import com.OnlineEvent.umangburman.event.Models.SpeakerProfileModel
import com.OnlineEvent.umangburman.event.Models.SpeakerResponseModel
import com.OnlineEvent.umangburman.event.Models.SpeakersResponseModel
import com.OnlineEvent.umangburman.event.Models.scheduleModels.ScheduleResponseModel
import com.OnlineEvent.umangburman.event.SchduleFragment.ScheduleRepository

class EventViewModel : ViewModel() {
    private var repositoryHelper: ScheduleRepository = ScheduleRepository()
    private lateinit var mutableLiveData: MutableLiveData<ScheduleResponseModel>
    private lateinit var speakerMutableLiveData: MutableLiveData<SpeakerResponseModel>
    private lateinit var singelSpeakerLiveData: MutableLiveData<SpeakerProfileModel>

    fun getEventData(currentPageNum: Int, accessToken: String) {
        mutableLiveData = repositoryHelper.getEvent(currentPageNum, accessToken)

    }

    fun getData(): MutableLiveData<ScheduleResponseModel> {
        return mutableLiveData
    }

    fun getEventSpeakerData(eventId: Int, accessToken: String) {
        speakerMutableLiveData = repositoryHelper.getEventSpeakers(eventId, accessToken)

    }

    fun getSingelEventSpeakerData(): MutableLiveData<SpeakerResponseModel> {
        return speakerMutableLiveData
    }

    fun getSingelSpeaker(speakerId: Int, accessToken: String) {
        singelSpeakerLiveData = repositoryHelper.getSingleSpeakerSpeakers(speakerId, accessToken)

    }

    fun getSingelSpeakerData(): MutableLiveData<SpeakerProfileModel> {
        return singelSpeakerLiveData
    }

}
