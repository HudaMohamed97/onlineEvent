package com.huda.mypatienttracker.NotificationFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.OnlineEvent.umangburman.event.NotificationFragment.NotificationRepository
import okhttp3.ResponseBody

class NotificationListViewModel : ViewModel() {
    private var repositoryHelper: NotificationRepository = NotificationRepository()
    private lateinit var mutableLiveData: MutableLiveData<ResponseBody>


    fun getNotification(auth: String) {
        mutableLiveData = repositoryHelper.getNotification(auth)

    }

    fun getNotificationData(): MutableLiveData<ResponseBody> {
        return mutableLiveData
    }

}
