package com.OnlineEvent.umangburman.event.DescriptionFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.OnlineEvent.umangburman.event.Models.EventDescriptionModel

class LearnMoreViewModel : ViewModel() {
    private var repositoryHelper: LearnMoreRepository = LearnMoreRepository()
    private lateinit var mutableLiveData: MutableLiveData<EventDescriptionModel>
    fun getEventDescriptionData(eventId: Int, accessToken: String) {
        mutableLiveData = repositoryHelper.getEventDescription(eventId, accessToken)
    }

    fun getData(): MutableLiveData<EventDescriptionModel> {
        return mutableLiveData
    }

}
