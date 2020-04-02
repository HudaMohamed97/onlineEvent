package com.OnlineEvent.umangburman.event.SchduleFragment

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
import com.OnlineEvent.umangburman.event.HomeFragment.HomeViewModel
import com.OnlineEvent.umangburman.event.MainActivity
import com.OnlineEvent.umangburman.event.R
import kotlinx.android.synthetic.main.about_fragment.*
import kotlinx.android.synthetic.main.first_fragment.backButton


class SchduleFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).setDrawerLocked(false)
        (activity as MainActivity).showItem("second")
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.about_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        backButton.setOnClickListener {
            (activity as MainActivity).showDrwer(true)
        }
        callAboutData()
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
