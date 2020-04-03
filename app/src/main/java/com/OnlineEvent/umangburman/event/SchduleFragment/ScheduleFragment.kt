package com.OnlineEvent.umangburman.event.SchduleFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.OnlineEvent.umangburman.event.Adapter.ScheduleAdapter
import com.OnlineEvent.umangburman.event.MainActivity
import com.OnlineEvent.umangburman.event.Models.SchduleResponseData
import com.OnlineEvent.umangburman.event.R
import kotlinx.android.synthetic.main.first_fragment.*


class ScheduleFragment : Fragment() {
    private lateinit var root: View
    private lateinit var scheduleViewModel: ScheduleViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private val modelFeedArrayList = arrayListOf<SchduleResponseData>()
    private lateinit var scheduleAdapter: ScheduleAdapter
    var mHasReachedBottomOnce = false
    var currentPageNum = 1
    var lastPageNum: Int = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).setDrawerLocked(false)
        (activity as MainActivity).showItem("second")
        scheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
        root = inflater.inflate(R.layout.schdule_fragment, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        callScheduleData(1, false)
    }

    private fun setClickListeners() {
        recyclerView = root.findViewById(R.id.scheduleRecycler)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        backButton.setOnClickListener {
            (activity as MainActivity).showDrwer(true)
        }

    }

    private fun callScheduleData(currentPageNum: Int, fromLoadMore: Boolean) {
        //scheduleProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            //  scheduleViewModel.getAbout(accessToken)
        }
        /*scheduleViewModel.getAboutData().observe(this, Observer {
            aboutProgressBar.visibility = View.GONE
            if (it != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    aboutDescription.text = Html.fromHtml(it.data.value, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    aboutDescription.text = Html.fromHtml(it.data.value)
                }
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })*/
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        scheduleAdapter = ScheduleAdapter(modelFeedArrayList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = scheduleAdapter
        /* scheduleAdapter.setOnCommentListener(object : CoePatientAdapter.OnCommentClickListener {
             override fun onDotsImageClicked(position: Int, fromTab: String) {
                 if (fromTab == "update") {
                     val bundle = Bundle()
                     bundle.putInt("PatientId", modelFeedArrayList[position].id)
                     findNavController().navigate(R.id.action_navigate_to_update, bundle)
                 } else if (fromTab == "") {
                     Toast.makeText(activity, "Please Select Action.", Toast.LENGTH_SHORT).show()

                 }

             }


         })*/

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !mHasReachedBottomOnce) {
                    mHasReachedBottomOnce = true
                    if (currentPageNum <= lastPageNum) {
                        callScheduleData(currentPageNum, true)

                    }
                }
            }
        })

    }


}
