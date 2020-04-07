package com.OnlineEvent.umangburman.event.SpeakerFragment

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
import androidx.recyclerview.widget.RecyclerView
import com.OnlineEvent.umangburman.event.Adapter.SpeakerAdapter
import com.OnlineEvent.umangburman.event.EventFragment.EventViewModel
import com.OnlineEvent.umangburman.event.Models.SpeakerData
import com.OnlineEvent.umangburman.event.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.schdule_fragment.scheduleProgressBar
import kotlinx.android.synthetic.main.speaker_profile.*

class SpeakerProfileFragment : Fragment() {
    private lateinit var root: View
    private var speakerId = 0
    private lateinit var eventViewModel: EventViewModel
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        root = inflater.inflate(R.layout.speaker_profile, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        speakerId = arguments?.getInt("SpeakerId")!!
        setClickListeners()
        callSingleSpeakersData(speakerId)
    }

    private fun setClickListeners() {
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
    }


    private fun callSingleSpeakersData(speakerId: Int) {
        speakerProfileProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            eventViewModel.getSingelSpeaker(speakerId, accessToken)
        }
        eventViewModel.getSingelSpeakerData().observe(this, Observer {
            speakerProfileProgressBar.visibility = View.GONE
            if (it != null) {
                Glide.with(context!!).load(it.data.photo).centerCrop()
                        .placeholder(R.drawable.profile)
                        .error(R.drawable.profile).into(speakerProfile)
                speaker_name.text = it.data.name
                speaker_address.text = it.data.address
                if (it.data.phone != null) {
                    speaker_phone.text = it.data.phone
                }
                speaker_mail.text = it.data.email
                speaker_Bio.movementMethod = ScrollingMovementMethod()
                if (it.data.bio != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        speaker_Bio.text = Html.fromHtml(it.data.bio, Html.FROM_HTML_MODE_COMPACT)
                    } else {
                        speaker_Bio.text = Html.fromHtml(it.data.bio)
                    }
                }


            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

}