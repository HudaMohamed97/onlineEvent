package com.OnlineEvent.umangburman.event.AboutFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.OnlineEvent.umangburman.event.HomeFragment.HomeViewModel
import com.OnlineEvent.umangburman.event.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.about_fragment.*
import kotlinx.android.synthetic.main.about_fragment.back
import kotlinx.android.synthetic.main.about_fragment.backButton
import kotlinx.android.synthetic.main.about_fragment.imgProfile
import kotlinx.android.synthetic.main.about_fragment.myevent_button
import kotlinx.android.synthetic.main.about_fragment.schedule_button
import kotlinx.android.synthetic.main.first_fragment.*
import kotlinx.android.synthetic.main.speaker_fragment.*


class AboutFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.about_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        setClickListener()
        callAboutData()
        setProfileImage()
    }

    private fun setClickListener() {
        back.setOnClickListener {
            activity?.finish()
        }
        backButton.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
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

    }

    private fun setProfileImage() {
        val photo = loginPreferences.getString("photo", "")
        Glide.with(context!!).load(photo).centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile).into(imgProfile)
    }

    private fun callAboutData() {
        aboutProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            homeViewModel.getAbout(accessToken)
        }
        homeViewModel.getAboutData().observe(this, Observer {
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
        })
    }


}
