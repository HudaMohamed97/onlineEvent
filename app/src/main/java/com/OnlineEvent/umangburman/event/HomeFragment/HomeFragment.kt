package com.OnlineEvent.umangburman.event.HomeFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.OnlineEvent.umangburman.event.MainActivity
import kotlinx.android.synthetic.main.first_fragment.*
import android.widget.ImageView
import android.widget.Toast
import android.net.Uri
import android.widget.MediaController
import com.OnlineEvent.umangburman.event.R




class HomeFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        val imageId = v?.id
        if (imageId == 1) {
            Toast.makeText(activity, "ImageClicked", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).setDrawerLocked(false)
        (activity as MainActivity).showItem("second")
        return inflater.inflate(R.layout.first_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButton.setOnClickListener {
            (activity as MainActivity).showDrwer(true)

        }
        val imageList = intArrayOf(R.drawable.ic_attach_money_black_24dp, R.drawable.ic_launcher_background, R.drawable.ic_save_black_24dp)
        for (image in imageList) {
            setFlipperImage(image)
        }
        button.setOnClickListener {
            nextView()
        }
        button2.setOnClickListener {
            previousView()
        }

        val mediaController = MediaController(activity)
        mediaController.setAnchorView(videoView)
        val uriPath = "https://www.demonuts.com/Demonuts/smallvideo.mp4"
        val uri = Uri.parse(uriPath)
        videoView.setZOrderOnTop(true)
        videoView.setVideoURI(uri)
        videoView.requestFocus()
        videoView.setMediaController(mediaController)
        videoView.start()

        videoView.setOnPreparedListener{

        }


    }

    @SuppressLint("ResourceType")
    private fun setFlipperImage(res: Int) {
        val image = ImageView(this.context)
        image.isClickable = true
        image.id = res
        image.setBackgroundResource(res)
        flipper.addView(image)
        image.setOnClickListener {
            if (image.id == R.drawable.ic_attach_money_black_24dp) {
                Toast.makeText(activity, "ImageClicked", Toast.LENGTH_SHORT).show()

            }
        }
        /* flipper.setFlipInterval(1000)
         flipper.startFlipping()*/
    }

    fun previousView() {
        flipper.setInAnimation(this.context, R.anim.slide_in_right)
        flipper.setOutAnimation(this.context, R.anim.slide_out_left)
        flipper.showPrevious()
    }

    fun nextView() {
        flipper.setInAnimation(this.context, android.R.anim.slide_in_left)
        flipper.setOutAnimation(this.context,
                android.R.anim.slide_out_right)
        flipper.showNext()
    }
}
