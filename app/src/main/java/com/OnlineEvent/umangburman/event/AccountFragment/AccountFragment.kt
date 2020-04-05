package com.OnlineEvent.umangburman.event.AccountFragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.OnlineEvent.umangburman.event.LoginFragment.LoginViewModel
import com.OnlineEvent.umangburman.event.MainActivity
import com.OnlineEvent.umangburman.event.R
import com.bumptech.glide.Glide
import com.example.myapplication.LoginFragment.LoginInterface
import kotlinx.android.synthetic.main.first_fragment.*
import kotlinx.android.synthetic.main.first_fragment.imgProfile
import kotlinx.android.synthetic.main.my_account.*
import kotlinx.android.synthetic.main.reset_password_fragment.*


class AccountFragment : Fragment(), LoginInterface {
    private lateinit var root: View
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.my_account, container, false)
        (activity as MainActivity).setDrawerLocked(true)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        setProfileImage()
    }

    override fun setClickListeners() {
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", MODE_PRIVATE)
    }

    private fun setProfileImage() {
        val photo = loginPreferences.getString("photo", "")
        Glide.with(context!!).load(photo).centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile).into(user_profile_photo)
    }


    private fun callResetPassword() {
        /* resetProgressBar.visibility = View.VISIBLE
         loginViewModel.resetPassword(
                 email.text.toString()
         )
         loginViewModel.getResetData().observe(this, Observer {
             resetProgressBar.visibility = View.GONE
             if (it != null) {
                 Toast.makeText(activity, "Password Reset Successfully", Toast.LENGTH_SHORT).show()
             } else {
                 Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
             }


         })*/

    }

}