package com.OnlineEvent.umangburman.event.EditPassword

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
import com.OnlineEvent.umangburman.event.EditAccount.EditViewModel
import com.OnlineEvent.umangburman.event.R
import com.bumptech.glide.Glide
import com.example.myapplication.LoginFragment.LoginInterface
import kotlinx.android.synthetic.main.edit_passsword.*
import kotlinx.android.synthetic.main.my_account.imgProfile


class EditPasswordFragment : Fragment(), LoginInterface {
    private lateinit var root: View
    private lateinit var editViewModel: EditViewModel
    private lateinit var loginPreferences: SharedPreferences
    private var currentPass: String = ""
    private var newPass: String = ""
    private var rePass: String = ""


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.edit_passsword, container, false)
        editViewModel = ViewModelProviders.of(this).get(EditViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        setProfileImage()
    }

    override fun setClickListeners() {
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
        myevent_button.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_Home_to_Event)
        }



        loginPreferences = activity!!.getSharedPreferences("loginPrefs", MODE_PRIVATE)
        submit_button.setOnClickListener {
            currentPass = oldPassword_editText.text.toString()
            newPass = newPass_editText.text.toString()
            rePass = rePass_editText.text.toString()
            if (currentPass.isEmpty() || newPass.isEmpty() || rePass.isEmpty()) {
                Toast.makeText(activity, "please enter All Fields", Toast.LENGTH_SHORT).show()

            } else if (currentPass.length < 6 || newPass.length < 6) {
                Toast.makeText(activity, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()

            } else if (newPass != rePass) {
                Toast.makeText(activity, "New Password And Re-password not matched", Toast.LENGTH_SHORT).show()
            } else {
                editAccountData(currentPass, newPass)
            }
        }

    }

    private fun setProfileImage() {
        val photo = loginPreferences.getString("photo", "")
        Glide.with(context!!).load(photo).centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile).into(imgProfile)
    }


    private fun editAccountData(currentPass: String, newPass: String) {
        passwordProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            editViewModel.updatePassword(currentPass, newPass, accessToken)
        }

        editViewModel.getPasswordStatues().observe(this, Observer {
            passwordProgressBar.visibility = View.GONE
            if (it != null) {
                Toast.makeText(activity, "Updated Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }

        })

    }
}