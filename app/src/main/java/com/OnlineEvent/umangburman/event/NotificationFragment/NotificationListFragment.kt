package com.huda.mypatienttracker.NotificationFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.OnlineEvent.umangburman.event.Adapter.PatientAdapter
import com.OnlineEvent.umangburman.event.Models.PatientResponseData
import com.OnlineEvent.umangburman.event.R


class NotificationListFragment : Fragment() {
    private lateinit var root: View
    private lateinit var notificationListViewModel: NotificationListViewModel
    private val modelFeedArrayList = arrayListOf<PatientResponseData>()
    private lateinit var patientAdapter: PatientAdapter
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
        root = inflater.inflate(R.layout.notification_fragment_list, container, false)
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

    /* private fun callPatients(page: Int, fromLoadMore: Boolean, fromRefresh: Boolean) {
         if (fromLoadMore) {
             LoadMoreNotificationProgressBar.visibility = View.VISIBLE
         } else {
             notificationProgressBar.visibility = View.VISIBLE
         }
         val accessToken = loginPreferences.getString("accessToken", "")
         if (accessToken != null) {
             patientaListViewModel.getPatients(page, "no update", "referal", accessToken)
         }
         patientaListViewModel.getData().observe(this, Observer {
             if (fromLoadMore) {
                 LoadMoreNotificationProgressBar.visibility = View.GONE
             } else {
                 modelFeedArrayList.clear()
                 notificationProgressBar.visibility = View.GONE
             }
             if (fromRefresh) {
                 currentPageNum = 1
                 modelFeedArrayList.clear()
             }
             if (it != null) {
                 currentPageNum = it.meta.current_page
                 lastPageNum = it.meta.last_page
                 for (data in it.data) {
                     modelFeedArrayList.add(data)
                 }
                 if (modelFeedArrayList.size == 0) {
                     *//*modelFeedArrayList.add(
                        PatientResponseData(
                            1,
                            "name",
                            "noUpdate",
                            null,
                            "kdkdsk"
                        )
                    )
                    patientAdapter.notifyDataSetChanged()*//*
                    Toast.makeText(activity, "No Patient Added Yet.", Toast.LENGTH_SHORT).show()

                }
                patientAdapter.notifyDataSetChanged()
                mHasReachedBottomOnce = false
                currentPageNum++

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
*/

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        patientAdapter = PatientAdapter(modelFeedArrayList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = patientAdapter
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