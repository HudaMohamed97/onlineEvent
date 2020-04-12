package com.OnlineEvent.umangburman.event.ResetPasswordFragment

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.OnlineEvent.umangburman.event.LoginFragment.LoginViewModel
import com.OnlineEvent.umangburman.event.R
import com.example.catapplication.utilies.Validation
import com.example.myapplication.LoginFragment.LoginInterface
import kotlinx.android.synthetic.main.edit_passsword.*
import kotlinx.android.synthetic.main.reset_password_fragment.*
import kotlinx.android.synthetic.main.reset_password_fragment.backButton


class ResetPasswordFragment : Fragment(), LoginInterface {
    private lateinit var root: View
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var email: EditText
    private lateinit var passwordEt: EditText
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var loginPrefsEditor: SharedPreferences.Editor


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.reset_password_fragment, container, false)
        // (activity as MainActivity).setDrawerLocked(true)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    override fun setClickListeners() {
        val mainLayout = root.findViewById(R.id.mainLayout) as View
        val button = root.findViewById(R.id.resetButton) as Button
        email = root.findViewById(R.id.input_email)
        mainLayout.setOnClickListener {
            hideKeyboard()
        }
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", MODE_PRIVATE)
        loginPrefsEditor = loginPreferences.edit()

        backButton.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
        back.setOnClickListener {
            activity!!.finish()
        }



        button.setOnClickListener {
            checkErrorEnabled()
            hideKeyboard()
            if (email.text.toString().isNotEmpty() || email.text.toString() != "") {
                callResetPassword()
            }
        }

    }

    private fun callResetPassword() {
        resetProgressBar.visibility = View.VISIBLE
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


        })

    }

    private fun checkErrorEnabled() {
        if (!Validation.validate(email.text.toString())) {
            Toast.makeText(activity, "empty Email please fill it", Toast.LENGTH_SHORT).show()
        } else if (!Validation.validateEmail(email.text.toString())) {
            Toast.makeText(
                    activity,
                    "Invalid Email Format Please enter valid mail",
                    Toast.LENGTH_SHORT
            ).show()

        }
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