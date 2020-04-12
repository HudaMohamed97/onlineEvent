package com.OnlineEvent.umangburman.event.NotificationFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.OnlineEvent.umangburman.event.Adapter.notificationAdapter
import com.OnlineEvent.umangburman.event.R
import com.huda.mypatienttracker.NotificationFragment.NotificationListViewModel
import kotlinx.android.synthetic.main.schdule_fragment.*


class NotificationListFragment : Fragment() {
    private lateinit var root: View
    private lateinit var notificationListViewModel: NotificationListViewModel
    private val modelFeedArrayList = arrayListOf<String>()
    private lateinit var notificationAdapter: notificationAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var loginPreferences: SharedPreferences
    var mHasReachedBottomOnce = false
    var currentPageNum = 1
    var lastPageNum: Int = 0


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.notification_fragment, container, false)
        notificationListViewModel =
                ViewModelProviders.of(this).get(NotificationListViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        setClickListeners()
        initRecyclerView()
        // callPatients(1, false, false)
    }

    private fun callScheduleData(pageNum: Int, fromLoadMore: Boolean) {
        scheduleProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            notificationListViewModel.getNotification(accessToken)
        }
        notificationListViewModel.getNotificationData().observe(this, Observer {
            scheduleProgressBar.visibility = View.GONE
            if (it != null) {
                /* lastPageNum = it.meta.last_page
                 currentPageNum = it.meta.current_page*/
               /* for (data in it.data) {
                    modelFeedArrayList.add(data)
                }
                if (modelFeedArrayList.size == 0) {
                    Toast.makeText(activity, "No Events Added Yet.", Toast.LENGTH_SHORT).show()
                }*/
                notificationAdapter.notifyDataSetChanged()
                mHasReachedBottomOnce = false
                currentPageNum++
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        // patientAdapter = PatientAdapter(modelFeedArrayList)
        recyclerView.layoutManager = layoutManager
        // recyclerView.adapter = patientAdapter
/*
        patientAdapter.setOnCommentListener(object : PatientAdapter.OnCommentClickListener {
            override fun onDotsImageClicked(position: Int, fromTab: String) {
                if (fromTab == "Confirmed") {
                    val bundle = Bundle()
                    bundle.putInt("PatientId", modelFeedArrayList[position].id)
                    // bundle.putInt("HospitalId", modelFeedArrayList[position].hospital?.id!!)
                    bundle.putInt("HospitalId", 1)
                    // bundle.putString("HospitalName", modelFeedArrayList[position].hospital?.name)
                    bundle.putString("HospitalName", "name")
                    */
/* findNavController().navigate(
                         R.id.action_PatientList_updateReferalPatientFragment,
                         bundle
                     )*//*


                } else if (fromTab == "") {
                    Toast.makeText(activity, "Please Select Action.", Toast.LENGTH_SHORT).show()

                }

            }


        })
*/

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !mHasReachedBottomOnce) {
                    mHasReachedBottomOnce = true
                    if (currentPageNum <= lastPageNum) {
                        //callPatients(currentPageNum, true, false)
                    }
                }
            }
        })

    }


    private fun setClickListeners() {
        currentPageNum = 1
        lastPageNum = 0
        val backButton = root.findViewById(R.id.backButton) as ImageView
        backButton.setOnClickListener {
            findNavController(this).navigateUp()
        }
        recyclerView = root.findViewById(R.id.notificationRecycler)


    }


}