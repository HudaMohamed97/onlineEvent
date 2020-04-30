package com.OnlineEvent.umangburman.event.PostsFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.OnlineEvent.umangburman.event.Adapter.AgendaAdapter
import com.OnlineEvent.umangburman.event.Models.AgendaDaysData
import com.OnlineEvent.umangburman.event.Models.Talks
import com.OnlineEvent.umangburman.event.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.agenda_fragment.*
import kotlinx.android.synthetic.main.agenda_fragment.about_button
import kotlinx.android.synthetic.main.agenda_fragment.imgProfile
import kotlinx.android.synthetic.main.agenda_fragment.myevent_button
import kotlinx.android.synthetic.main.agenda_fragment.schedule_button
import kotlinx.android.synthetic.main.agenda_fragment.speakersTab
import kotlinx.android.synthetic.main.speaker_fragment.descriptionTab

class PostsFragment : Fragment() {
    private var root: View? = null
    private var eventId = 0
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private val modelFeedArrayList = arrayListOf<AgendaDaysData>()
    private val agendaArrayList = arrayListOf<Talks>()
    private lateinit var agendaAdapter: AgendaAdapter
    var listDays = arrayListOf<Int>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (root != null) {
            NavHostFragment.findNavController(this).navigateUp()
            root
        } else {
            root = inflater.inflate(R.layout.post_fragment, container, false)
            root
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventId = arguments?.getInt("EventId")!!
        setClickListeners()
        initRecyclerView()
        setProfileImage()
    }

    private fun setClickListeners() {
      /*  descriptionTab.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("EventId", eventId)
            NavHostFragment.findNavController(this).navigate(R.id.action_Event_ToDescription, bundle)

        }
        speakersTab.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("EventId", eventId)
            NavHostFragment.findNavController(this).navigate(R.id.action_Event_To_Speakers, bundle)
        }
        backButton.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()

        }
        about_button.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_Home_to_About)

        }
        schedule_button.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_Home_to_Schedule)

        }
        myevent_button.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_Home_to_Event)

        }
        imgProfile.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_Home_to_muyAccount)
        }

*/
        recyclerView = root!!.findViewById(R.id.postRecycler)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
    }


    private fun setProfileImage() {
        val photo = loginPreferences.getString("photo", "")
        Glide.with(context!!).load(photo).centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile).into(imgProfile)
    }




    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        agendaAdapter = AgendaAdapter(agendaArrayList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = agendaAdapter
    }

}