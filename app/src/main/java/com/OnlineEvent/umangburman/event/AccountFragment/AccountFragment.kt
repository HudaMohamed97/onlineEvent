package com.OnlineEvent.umangburman.event.AccountFragment

import android.content.Context.MODE_PRIVATE
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
import com.OnlineEvent.umangburman.event.HomeFragment.HomeViewModel
import com.OnlineEvent.umangburman.event.MainActivity
import com.OnlineEvent.umangburman.event.R
import com.bumptech.glide.Glide
import com.example.myapplication.LoginFragment.LoginInterface
import kotlinx.android.synthetic.main.my_account.*
import kotlinx.android.synthetic.main.reset_password_fragment.*


class AccountFragment : Fragment(), LoginInterface {
    private lateinit var root: View
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.my_account, container, false)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        setProfileImage()
        callMyAccountData()
    }

    override fun setClickListeners() {
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", MODE_PRIVATE)
        editButton.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_Account_to_editAccount)

        }
    }

    private fun setProfileImage() {
        val photo = loginPreferences.getString("photo", "")
        Glide.with(context!!).load(photo).centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile).into(imgProfile)
    }


    private fun callMyAccountData() {
        myAccountProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            homeViewModel.getMyAccountData(accessToken)
        }

        homeViewModel.AaccountData().observe(this, Observer {
            myAccountProgressBar.visibility = View.GONE
            if (it != null) {
                Glide.with(context!!).load(it.account.photo).centerCrop()
                        .placeholder(R.drawable.profile)
                        .error(R.drawable.profile).into(user_profile_photo)
                name.text = "Name: " + it.account.name
                account_email.text = "Email: " + it.account.email
                Phone.text = "Phone: " + it.account.phone


            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }


        })

    }

}