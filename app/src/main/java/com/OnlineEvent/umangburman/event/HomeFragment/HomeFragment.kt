package com.OnlineEvent.umangburman.event.HomeFragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.OnlineEvent.umangburman.event.MainActivity
import com.OnlineEvent.umangburman.event.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.first_fragment.*
import android.media.MediaMetadataRetriever
import android.os.Build
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable


class HomeFragment : Fragment() {
    private var root: View? = null
    private var fromBack = false
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var video_view: VideoView
    private lateinit var loginPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).setDrawerLocked(false)
        (activity as MainActivity).showItem("second")
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        return if (root != null) {
            fromBack = true
            root
        } else {
            fromBack = false
            root = inflater.inflate(R.layout.first_fragment, container, false)
            root
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        if (!fromBack) {
            callHomeData()
        }
        setListeners()
        setProfileImage()
    }

    private fun setListeners() {
        backButton.setOnClickListener {
            (activity as MainActivity).showDrwer(true)
        }
        val mediaController = MediaController(activity)
        mediaController.setAnchorView(videoView)
        videoView.setZOrderOnTop(true)
        //videoView.setZOrderMediaOverlay(true)
        videoView.requestFocus()
        videoView.setMediaController(mediaController)
        button.setOnClickListener {
            nextView()
        }
        button2.setOnClickListener {
            previousView()
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
        back.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
    }

    private fun setProfileImage() {
        val photo = loginPreferences.getString("photo", "")
        Glide.with(context!!).load(photo).centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile).into(imgProfile)
    }


    private fun loadVideo(uri: String) {
        val uriPath = uri
        val uri = Uri.parse(uriPath)
        videoView.setZOrderOnTop(true)
        //videoView.setZOrderMediaOverlay(true)
        videoView.setVideoURI(uri)
        val mediaController = MediaController(this.context)
        mediaController.show(1)
        videoView.setMediaController(mediaController)
        videoView.start()
        videoView.setOnPreparedListener {
            videoProgressBar.visibility = View.GONE
            it.isLooping = true
        }
    }

    private fun callHomeData() {
        HomeProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            homeViewModel.getHomeData(accessToken)
        }
        homeViewModel.homeData().observe(this, Observer {
            HomeProgressBar.visibility = View.GONE
            if (it != null) {
                mainLayout.visibility = View.VISIBLE
                videoProgressBar.visibility = View.VISIBLE
                //loadVideo(it.video)
                loadVideo("https://www.demonuts.com/Demonuts/smallvideo.mp4")
                val imageList = arrayListOf<String>()
                val videoList = arrayListOf<String>()
                for (image in it.sliders) {
                    if (image.is_image) {
                        imageList.add(image.photo)
                    } else {
                        videoList.add(image.video_url)
                    }
                }

                setFlipperImage(imageList, videoList)
                Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()

            } else {
                mainLayout.visibility = View.GONE
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    @SuppressLint("ResourceType")
    private fun setFlipperImage(res: ArrayList<String>, videoList: ArrayList<String>) {
        var count = 0
        var imagesView: ImageView? = null
        for (image in res) {
            if (count == 0) {
                imagesView = ImageView(this.context)
                imagesView.isClickable = true
                imagesView.id = 0
                Glide.with(context!!).load(image).centerCrop()
                        .placeholder(R.drawable.ic_save_black_24dp)
                        .error(R.drawable.ic_save_black_24dp).into(imagesView)

                // imagesView.setBackgroundResource(res)
                flipper.addView(imagesView)
                count++
            } else {
                imagesView = ImageView(this.context)
                imagesView.isClickable = true
                imagesView.id = 1
                Glide.with(context!!).load(image).centerCrop()
                        .placeholder(R.drawable.ic_save_black_24dp)
                        .error(R.drawable.ic_save_black_24dp).into(imagesView)
                flipper.addView(imagesView)
                count++
            }
        }
        for (video in videoList) {
            video_view = VideoView(this.context)
            val uri = Uri.parse("https://www.demonuts.com/Demonuts/smallvideo.mp4")
            var bitmap = retriveVideoFrameFromVideo("https://www.demonuts.com/Demonuts/smallvideo.mp4")
            if (bitmap != null) {
                bitmap = Bitmap.createScaledBitmap(bitmap, 240, 240, false)
            }
            video_view.setZOrderOnTop(true)

            //video_view.setZOrderMediaOverlay(true)
            video_view.setVideoURI(uri)
            var ob: Drawable = BitmapDrawable(resources, bitmap)
            video_view.background = ob
            flipper.addView(video_view)
            video_view.setOnPreparedListener {
                it.isLooping = true
                video_view.start()
                video_view.setBackgroundResource(R.color.transparent)
            }
        }

        imagesView?.setOnClickListener {
            if (imagesView.id == 0) {
                Toast.makeText(activity, "ImageClicked", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun retriveVideoFrameFromVideo(videoPath: String): Bitmap? {
        var bitmap: Bitmap?
        var mediaMetadataRetriever: MediaMetadataRetriever? = null
        try {
            mediaMetadataRetriever = MediaMetadataRetriever()
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, HashMap())
            else
                mediaMetadataRetriever.setDataSource(videoPath)

            bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST)
        } catch (e: Exception) {
            e.printStackTrace()
            throw Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)" + e.message)
        } finally {
            mediaMetadataRetriever?.release()
        }
        return bitmap
    }

    private fun previousView() {
        flipper.setInAnimation(this.context, R.anim.slide_in_right)
        flipper.setOutAnimation(this.context, R.anim.slide_out_left)
        flipper.showPrevious()
    }

    private fun nextView() {
        flipper.setInAnimation(this.context, android.R.anim.slide_in_left)
        flipper.setOutAnimation(this.context,
                android.R.anim.slide_out_right)
        flipper.showNext()
    }
}
