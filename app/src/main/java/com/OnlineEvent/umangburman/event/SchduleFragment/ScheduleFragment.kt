package com.OnlineEvent.umangburman.event.SchduleFragment

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
import com.OnlineEvent.umangburman.event.Models.DataSpeaker
import com.OnlineEvent.umangburman.event.Models.scheduleModels.ScheduleResponse
import com.OnlineEvent.umangburman.event.R
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.first_fragment.backButton
import kotlinx.android.synthetic.main.schdule_fragment.*
import kotlinx.android.synthetic.main.schdule_fragment.about_button
import kotlinx.android.synthetic.main.schdule_fragment.back
import kotlinx.android.synthetic.main.schdule_fragment.imgProfile
import kotlinx.android.synthetic.main.schdule_fragment.myevent_button
import kotlinx.android.synthetic.main.schdule_fragment.scheduleProgressBar
import kotlinx.android.synthetic.main.speaker_fragment.*
import kotlinx.android.synthetic.main.speaker_profile.*
import java.util.*


class ScheduleFragment : Fragment() {
    private lateinit var root: View
    private lateinit var scheduleViewModel: ScheduleViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private val modelFeedArrayList = arrayListOf<ScheduleResponse>()
    private lateinit var scheduleAdapter: ScheduleAdapter
    var mHasReachedBottomOnce = false
    var currentPageNum = 1
    private val monthList = arrayListOf<String>()
    var lastPageNum: Int = 0
    private var month: String = ""
    private var speakerId: Int = -1
    private var topic: String = ""
    private val speakersNameList = arrayListOf<String>()
    private val speakerList = arrayListOf<DataSpeaker>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        scheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
        root = inflater.inflate(R.layout.schdule_fragment, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        callScheduleData(1, false)
        initRecyclerView()
        initializeMonthSpinner()
        callSpeakersData()
    }

    private fun setClickListeners() {
        month = ""
        speakerId = -1
        topic = ""
        recyclerView = root.findViewById(R.id.scheduleRecycler)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        back.setOnClickListener {
            activity!!.finish()
        }
        filterScheduleButton.setOnClickListener {
            topic = filterTopic.text.toString()
            if (month != "" || speakerId != -1 || topic != "")
                if (month == "All Months") {
                    callScheduleData(1, false)
                } else {
                    callScheduleDataByFilter(topic, month, -1, 1, false)
                }
            else {
                Toast.makeText(activity, "please Choose filter Type", Toast.LENGTH_SHORT).show()

            }
        }
        about_button.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_Home_to_About)

        }
        myevent_button.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_Home_to_Event)

        }
        imgProfile.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_Home_to_muyAccount)
        }
        backButton.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
        back.setOnClickListener {
            activity!!.finish()
        }


    }

    private fun initializeMonthSpinner() {
        monthList.clear()
        monthList.add("All Months")
        for (i in 1..12) {
            monthList.add(i.toString())
        }
        initializeMonthSpinner(monthSpinner, monthList)
    }


    private fun initializeMonthSpinner(
            spinnerType: SearchableSpinner,
            typeList: ArrayList<String>
    ) {
        val arrayAdapter =
                context?.let {
                    ArrayAdapter(
                            it,
                            R.layout.support_simple_spinner_dropdown_item,
                            typeList
                    )
                }

        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parentView: AdapterView<*>,
                    selectedItemView: View,
                    position: Int,
                    id: Long
            ) {
                hideKeyboard()
                month = monthList[position]
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }

        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            spinnerType.adapter = arrayAdapter
        }
    }

    private fun callScheduleData(pageNum: Int, fromLoadMore: Boolean) {
        scheduleProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            scheduleViewModel.getSchedulesData(pageNum, accessToken)
        }
        scheduleViewModel.getData().observe(this, Observer {
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

    private fun callSpeakersData() {
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            scheduleViewModel.getSpeakers(accessToken)
        }
        scheduleViewModel.getSpeakerData().observe(this, Observer {
            if (it != null) {
                lastPageNum = it.meta.last_page
                currentPageNum = it.meta.current_page
                for (data in it.data) {
                    speakerList.add(data)
                }
                prepareSpeakerList(speakerList)
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun prepareSpeakerList(list: ArrayList<DataSpeaker>) {
        speakersNameList.clear()
        for (speaker in list) {
            speakersNameList.add(speaker.name)
        }
        initializeSpeakerSpinner(speakersSpinner, speakersNameList)
    }

    private fun callScheduleDataByFilter(filterTopic: String, month: String, speaker: Int, pageNum: Int, fromLoadMore: Boolean) {
        scheduleProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            scheduleViewModel.getSchedulesByFilter(month, speaker, filterTopic, pageNum, accessToken)
        }
        scheduleViewModel.getFilteredData().observe(this, Observer {
            scheduleProgressBar.visibility = View.GONE
            if (it != null) {
                modelFeedArrayList.clear()
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

    private fun initializeSpeakerSpinner(
            speakerSpinner: SearchableSpinner,
            speakersNameList: ArrayList<String>
    ) {
        val arrayAdapter =
                context?.let {
                    ArrayAdapter(
                            it,
                            R.layout.support_simple_spinner_dropdown_item,
                            speakersNameList
                    )
                }

        speakerSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parentView: AdapterView<*>,
                    selectedItemView: View,
                    position: Int,
                    id: Long
            ) {
                speakerId = speakerList[position].id
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }


        }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            speakerSpinner.adapter = arrayAdapter
        }

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
                        // callScheduleData(currentPageNum, true)

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
