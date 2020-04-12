package com.OnlineEvent.umangburman.event.SpeakerFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.OnlineEvent.umangburman.event.Adapter.SpeakerAdapter
import com.OnlineEvent.umangburman.event.EventFragment.EventViewModel
import com.OnlineEvent.umangburman.event.Models.SpeakerData
import com.OnlineEvent.umangburman.event.R
import kotlinx.android.synthetic.main.schdule_fragment.scheduleProgressBar
import kotlinx.android.synthetic.main.speaker_fragment.*
import kotlinx.android.synthetic.main.speaker_fragment.agenda_button
import kotlinx.android.synthetic.main.speaker_fragment.descriptionTab

class SpeakerFragment : Fragment() {
    private lateinit var root: View
    private var eventId = 0
    private lateinit var eventViewModel: EventViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private val modelFeedArrayList = arrayListOf<SpeakerData>()
    private lateinit var speakerAdapter: SpeakerAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       /* (activity as MainActivity).setDrawerLocked(false)
        (activity as MainActivity).showItem("second")*/
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        root = inflater.inflate(R.layout.speaker_fragment, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventId = arguments?.getInt("EventId")!!
        setClickListeners()
        callSpeakersData(eventId, false)
        initRecyclerView()
    }

    private fun setClickListeners() {
        modelFeedArrayList.clear()
        agenda_button.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("EventId", eventId)
            NavHostFragment.findNavController(this).navigate(R.id.action_Speaker_AgendaFragment, bundle)
        }

        descriptionTab.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("EventId", eventId)
            NavHostFragment.findNavController(this).navigate(R.id.action_Event_ToDescription, bundle)

        }
        back_Button.setOnClickListener{
            NavHostFragment.findNavController(this).navigateUp()

        }
        back.setOnClickListener {
            activity!!.finish()
        }

        recyclerView = root.findViewById(R.id.scheduleRecycler)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
    }


    private fun callSpeakersData(eventId: Int, fromLoadMore: Boolean) {
        scheduleProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            eventViewModel.getEventSpeakerData(eventId, accessToken)
        }
        eventViewModel.getSingelEventSpeakerData().observe(this, Observer {
            scheduleProgressBar.visibility = View.GONE
            if (it != null) {
                for (data in it.data) {
                    modelFeedArrayList.add(data)
                }
                if (modelFeedArrayList.size == 0) {
                    Toast.makeText(activity, "No Speakers Added Yet.", Toast.LENGTH_SHORT).show()
                }
                speakerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        speakerAdapter = SpeakerAdapter(modelFeedArrayList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = speakerAdapter
        val context = this
        speakerAdapter.setOnCommentListener(object : SpeakerAdapter.OnClickListener {
            override fun onItemClicked(position: Int) {
                val speakerId = modelFeedArrayList[position].id
                val bundle = Bundle()
                bundle.putInt("SpeakerId", speakerId)
                NavHostFragment.findNavController(context).navigate(R.id.action_Speaker_toSpeakerProfile, bundle)
            }


        })
    }

}