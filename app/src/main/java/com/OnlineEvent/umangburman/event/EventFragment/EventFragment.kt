package com.OnlineEvent.umangburman.event.EventFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.OnlineEvent.umangburman.event.Adapter.ScheduleAdapter
import com.OnlineEvent.umangburman.event.Models.scheduleModels.ScheduleResponse
import com.OnlineEvent.umangburman.event.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.event_fragment.*
import kotlinx.android.synthetic.main.event_fragment.about_button
import kotlinx.android.synthetic.main.event_fragment.back
import kotlinx.android.synthetic.main.event_fragment.backButton
import kotlinx.android.synthetic.main.event_fragment.imgProfile
import kotlinx.android.synthetic.main.event_fragment.schedule_button


class EventFragment : Fragment() {
    private lateinit var root: View
    private lateinit var eventViewModel: EventViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private val modelFeedArrayList = arrayListOf<ScheduleResponse>()
    private lateinit var scheduleAdapter: ScheduleAdapter
    var mHasReachedBottomOnce = false
    var currentPageNum = 1
    var lastPageNum: Int = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        root = inflater.inflate(R.layout.event_fragment, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        callMyEventsData(1, false)
        initRecyclerView()
        setProfileImage()
    }

    private fun setClickListeners() {
        modelFeedArrayList.clear()
        recyclerView = root.findViewById(R.id.scheduleRecycler)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        backButton.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()

        }
        back.setOnClickListener {
            activity!!.finish()
        }
        about_button.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_Home_to_About)

        }
        schedule_button.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_Home_to_Schedule)

        }
        imgProfile.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_Home_to_muyAccount)
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

    private fun setProfileImage() {
        val photo = loginPreferences.getString("photo", "")
        Glide.with(context!!).load(photo).centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile).into(imgProfile)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        scheduleAdapter = ScheduleAdapter(modelFeedArrayList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = scheduleAdapter
        val context = this
        scheduleAdapter.setOnCommentListener(object : ScheduleAdapter.OnClickListener {
            override fun onRegisterEventClicked(position: Int) {
            }

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
