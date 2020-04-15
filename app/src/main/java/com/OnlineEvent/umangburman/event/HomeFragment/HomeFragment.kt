package com.OnlineEvent.umangburman.event.HomeFragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.OnlineEvent.umangburman.event.Adapter.AdsAdapter
import com.OnlineEvent.umangburman.event.Adapter.ScheduleAdapter
import com.OnlineEvent.umangburman.event.Models.HomeModels.Ads
import com.OnlineEvent.umangburman.event.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.first_fragment.*
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    private var root: View? = null
    private var fromBack = false
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var video_view: VideoView
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private val modelFeedArrayList = arrayListOf<Ads>()
    private lateinit var adsAdapter: AdsAdapter
    private lateinit var mediaController: MediaController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
            setCounter()
        }
        setListeners()
        setProfileImage()
        initRecyclerView()
    }

    private fun setListeners() {
        scrollView.post {
            run {
                scrollView.fullScroll(View.FOCUS_UP)
                scrollView.smoothScrollTo(0, 0)

            }
        }

        videoView.start()
        videoLayout.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
                play_button.visibility = View.VISIBLE
                videoView.visibility = View.GONE
            } else {
                play_button.visibility = View.GONE
                videoView.visibility = View.VISIBLE
                videoProgressBar.visibility = View.VISIBLE
                videoView.start()
            }
        }
        play_button.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
                play_button.visibility = View.VISIBLE
                videoView.visibility = View.GONE
            } else {
                play_button.visibility = View.GONE
                videoView.visibility = View.VISIBLE
                videoProgressBar.visibility = View.VISIBLE
                videoView.start()
            }
        }

        videoView.requestFocus()
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
            activity?.finish()

        }
    }

    private fun setCounter() {
        val duration = 21600000 //4   //3 600 000 millisecond per hour
        object : CountDownTimer(duration.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var millisUntilFinished = millisUntilFinished
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60

                val elapsedHours = millisUntilFinished / hoursInMilli
                millisUntilFinished %= hoursInMilli

                val elapsedMinutes = millisUntilFinished / minutesInMilli
                millisUntilFinished %= minutesInMilli

                val elapsedSeconds = millisUntilFinished / secondsInMilli

                val yy = String.format("%02d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds)
                Log.i("hhhhh", "timer sha8alll" + yy)
                if (time != null) {
                    time.text = "Time Remaining : " + yy
                }
            }

            override fun onFinish() {
                if (time != null) {
                    time.text = "Time Remaining : " + "00:00:00"
                }
            }
        }.start()
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
        // val mediaController = MediaController(this.context)
        //  mediaController.show(1)
        // videoView.setMediaController(mediaController)
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
                val time = it.event.remaining_time
                val splitStr = time.split(" ")

                mainLayout.visibility = View.VISIBLE
                videoProgressBar.visibility = View.VISIBLE
                loadVideo("https://www.demonuts.com/Demonuts/smallvideo.mp4")
                // loadVideo(it.video)
                val imageList = arrayListOf<String>()
                val videoList = arrayListOf<String>()
                for (image in it.sliders) {
                    if (image.is_image) {
                        imageList.add(image.photo)
                    } else {
                        videoList.add(image.video_url)
                    }
                }
                for (item in it.ads) {
                    modelFeedArrayList.add(item)
                }
                scrollView.post {
                    run {
                        scrollView.fullScroll(View.FOCUS_UP)
                        scrollView.smoothScrollTo(0, 0)

                    }
                }
                adsAdapter.notifyDataSetChanged()
                setFlipperImage(imageList, videoList)

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
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background).into(imagesView)

                // imagesView.setBackgroundResource(res)
                flipper.addView(imagesView)
                count++
            } else {
                imagesView = ImageView(this.context)
                imagesView.isClickable = true
                imagesView.id = 1
                Glide.with(context!!).load(image).centerCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background).into(imagesView)
                flipper.addView(imagesView)
                count++
            }
        }
        for (video in videoList) {
            video_view = VideoView(this.context)
            val uri = Uri.parse("https://www.demonuts.com/Demonuts/smallvideo.mp4")
            /*var bitmap = retriveVideoFrameFromVideo("https://www.demonuts.com/Demonuts/smallvideo.mp4")
            if (bitmap != null) {
                bitmap = Bitmap.createScaledBitmap(R.mipmap.video_img, 240, 240, false)
            }*/
            video_view.setZOrderOnTop(true)

            //video_view.setZOrderMediaOverlay(true)
            video_view.setVideoURI(uri)
            //var ob: Drawable = BitmapDrawable(resources,R.mipmap.video_img)
            video_view.setBackgroundResource(R.mipmap.video_img)
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

    private fun initRecyclerView() {
        recyclerView = root?.findViewById(R.id.adsRecycler)!!
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adsAdapter = AdsAdapter(modelFeedArrayList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adsAdapter
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
