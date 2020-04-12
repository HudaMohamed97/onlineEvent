package com.OnlineEvent.umangburman.event.EditAccount

import android.Manifest
import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.OnlineEvent.umangburman.event.R
import com.bumptech.glide.Glide
import com.example.myapplication.LoginFragment.LoginInterface
import kotlinx.android.synthetic.main.edit_account.*
import kotlinx.android.synthetic.main.my_account.*
import kotlinx.android.synthetic.main.my_account.imgProfile


class EditFragment : Fragment(), LoginInterface {
    private lateinit var root: View
    private lateinit var editViewModel: EditViewModel
    private lateinit var loginPreferences: SharedPreferences
    private var fileUri: String = ""
    private var userEmail: String = ""
    private var userName: String = ""
    private var userBio: String = ""


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.edit_account, container, false)
        editViewModel = ViewModelProviders.of(this).get(EditViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        setProfileImage()
    }

    override fun setClickListeners() {
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", MODE_PRIVATE)
        email_editText.hint = loginPreferences.getString("email", "")
        name_editText.hint = loginPreferences.getString("Name", "")


        upload_image.setOnClickListener {
            isStoragePermissionGranted()
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            this.startActivityForResult(photoPickerIntent, 1)
        }

        submit_button.setOnClickListener {
            editAccountData()
        }

    }

    private fun setProfileImage() {
        val photo = loginPreferences.getString("photo", "")
        Glide.with(context!!).load(photo).centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile).into(imgProfile)
    }


    private fun editAccountData() {
        userName = name_editText.text.toString()
        userEmail = email_editText.text.toString()
        userBio = bio_editText.text.toString()
        if (fileUri == "") {
            val photo = loginPreferences.getString("photo", "")
            fileUri = photo
        }
        if (email_editText.text.toString() == "") {
            userEmail = email_editText.hint.toString()
        }
        if (name_editText.text.toString() == "") {
            userName = name_editText.hint.toString()
        }
        if (bio_editText.text.toString() == "") {
            userBio = name_editText.hint.toString()
        }
        editAccountProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            editViewModel.updateAccount(fileUri, userEmail, userName, userBio, accessToken)
        }

        editViewModel.getStatues().observe(this, Observer {
            editAccountProgressBar.visibility = View.GONE
            if (it != null) {
                Toast.makeText(activity, "Updated Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }


        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1)
            if (resultCode == Activity.RESULT_OK) {
                val selectedImage = data?.data
                fileUri = selectedImage?.let { getPath(it) }.toString()
                imageProfile.setImageURI(selectedImage)
                text.visibility = View.GONE
                imageProfile.visibility = View.VISIBLE
            }

    }

    private fun getPath(uri: Uri): String {
        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor = activity!!.contentResolver.query(uri, projection, null, null, null);
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        cursor?.moveToFirst()
        if (columnIndex != null) {
            cursor.getString(columnIndex)
        }

        return columnIndex?.let { cursor.getString(it) }!!
    }

    private fun isStoragePermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return if (ContextCompat.checkSelfPermission(
                            activity!!,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
            ) {
                true
            } else {
                ActivityCompat.requestPermissions(
                        activity!!,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        1
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show()


            //resume tasks needing this permission
        } else {
            Toast.makeText(activity, "not access", Toast.LENGTH_SHORT).show()
        }
    }


}