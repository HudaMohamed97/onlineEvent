package com.OnlineEvent.umangburman.event.EventFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.OnlineEvent.umangburman.event.Adapter.ScheduleAdapter
import com.OnlineEvent.umangburman.event.MainActivity
import com.OnlineEvent.umangburman.event.Models.scheduleModels.ScheduleResponse
import com.OnlineEvent.umangburman.event.R
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.first_fragment.backButton
import kotlinx.android.synthetic.main.schdule_fragment.*
import kotlinx.android.synthetic.main.schedule_row_list.*
import java.util.ArrayList


class EventFragment : Fragment() {
    private lateinit var root: View
    private lateinit var eventViewModel: EventViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private val modelFeedArrayList = arrayListOf<ScheduleResponse>()
    private lateinit var scheduleAdapter: ScheduleAdapter
    var mHasReachedBottomOnce = false
    var currentPageNum = 1
    private val monthList = arrayListOf<String>()
    var lastPageNum: Int = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).setDrawerLocked(false)
        (activity as MainActivity).showItem("second")
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        root = inflater.inflate(R.layout.event_fragment, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        callMyEventsData(1, false)
        initRecyclerView()
    }

    private fun setClickListeners() {
        modelFeedArrayList.clear()
        recyclerView = root.findViewById(R.id.scheduleRecycler)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        backButton.setOnClickListener {
            (activity as MainActivity).showDrwer(true)
        }
    }


    private fun callMyEventsData(pageNum: Int, fromLoadMore: Boolean) {
        scheduleProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            eventViewModel.getEventData(pageNum, accessToken)
        }
        eventViewModel.getData().observe(this, Observer {
            scheduleProgressBar.visibility = View.GONE
            if (it != null) {
                lastPageNum = it.meta.last_page
                currentPageNum = it.meta.current_page
                for (data in it.data) {
                    modelFeedArrayList.add(data)
                }
                if (modelFeedArrayList.size == 0) {
                    Toast.makeText(activity, "No Events Added Yet.", Toast.LENGTH_SHORT).show()
                }
                scheduleAdapter.notifyDataSetChanged()
                mHasReachedBottomOnce = false
                currentPageNum++
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        scheduleAdapter = ScheduleAdapter(modelFeedArrayList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = scheduleAdapter
        val context = this
        scheduleAdapter.setOnCommentListener(object : ScheduleAdapter.OnClickListener {
            override fun onItemClicked(position: Int) {
                val eventId = modelFeedArrayList[position].id
                val bundle = Bundle()
                bundle.putInt("EventId", eventId)
                NavHostFragment.findNavController(context).navigate(R.id.action_Event_ToDescription, bundle)
            }


        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !mHasReachedBottomOnce) {
                    mHasReachedBottomOnce = true
                    if (currentPageNum <= lastPageNum) {
                        callMyEventsData(currentPageNum, true)

                    }
                }
            }
        })

    }

    private fun hideKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            val imm =
                    context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}