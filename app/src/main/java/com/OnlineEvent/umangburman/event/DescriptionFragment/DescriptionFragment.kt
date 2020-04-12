package com.OnlineEvent.umangburman.event.DescriptionFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.OnlineEvent.umangburman.event.R
import kotlinx.android.synthetic.main.description_fragment.*
import kotlinx.android.synthetic.main.description_fragment.back
import kotlinx.android.synthetic.main.description_fragment.backButton
import kotlinx.android.synthetic.main.event_fragment.*


class DescriptionFragment : Fragment() {
    private var root: View? = null
    private lateinit var learnMoreViewModel: LearnMoreViewModel
    private lateinit var loginPreferences: SharedPreferences
    private var eventId: Int = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        learnMoreViewModel = ViewModelProviders.of(this).get(LearnMoreViewModel::class.java)
        return if (root != null) {
            NavHostFragment.findNavController(this).navigateUp()
            root
        } else {
            root = inflater.inflate(R.layout.description_fragment, container, false)
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventId = arguments?.getInt("EventId")!!
        setClickListeners()
        callEventsDescription(eventId)
    }

    private fun setClickListeners() {
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        speakersTab.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("EventId", eventId)
            NavHostFragment.findNavController(this).navigate(R.id.action_Event_To_Speakers, bundle)
        }
        agenda_button.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("EventId", eventId)
            NavHostFragment.findNavController(this).navigate(R.id.action_Speaker_AgendaFragment, bundle)
        }

        backButton.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()

        }
        back.setOnClickListener {
            activity!!.finish()
        }

    }


    private fun callEventsDescription(eventId: Int) {
        eventDescriptionProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            learnMoreViewModel.getEventDescriptionData(eventId, accessToken)
        }
        learnMoreViewModel.getData().observe(this, Observer {
            eventDescriptionProgressBar.visibility = View.GONE
            if (it != null) {
                date.text = it.data.start_date
                EventName.text = it.data.name
                time.text = it.data.start_date + "  to  " + it.data.end_date
                eventDescription.movementMethod = ScrollingMovementMethod()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    eventDescription.text = Html.fromHtml(it.data.description, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    eventDescription.text = Html.fromHtml(it.data.description)
                }
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
