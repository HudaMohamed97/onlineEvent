package com.OnlineEvent.umangburman.event.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.OnlineEvent.umangburman.event.Models.HomeModels.Ads
import com.OnlineEvent.umangburman.event.R
import com.bumptech.glide.Glide
import java.util.*


class AdsAdapter(modelFeedArrayList: ArrayList<Ads>) :
        RecyclerView.Adapter<AdsAdapter.MyViewHolder>() {

    private var context: Context? = null
    lateinit var onItemClickListener: OnClickListener


    override fun getItemCount(): Int {
        return modelFeedArrayList.size
    }

    var modelFeedArrayList = ArrayList<Ads>()


    init {
        this.modelFeedArrayList = modelFeedArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.ads_row, parent, false)
        context = parent.context
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val modelFeed = modelFeedArrayList[position]
        if (modelFeed.is_image) {
            holder.videoLayout.visibility = View.GONE
            holder.imageView.visibility = View.VISIBLE
            Glide.with(context!!).load(modelFeed.photo).centerCrop()
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile).into(holder.imageView)
        } else {
            holder.videoLayout.visibility = View.VISIBLE
            holder.imageView.visibility = View.GONE
            holder.videoView.requestFocus()
            val uriPath = modelFeed.video_url
            //val uri = Uri.parse(uriPath)
            val uri = Uri.parse("https://www.demonuts.com/Demonuts/smallvideo.mp4")
            holder.videoView.setZOrderOnTop(true)
            holder.videoView.setVideoURI(uri)
            //val mediaController = MediaController(this.context)
            //mediaController.setAnchorView(holder.videoView)
           // holder.videoView.setMediaController(mediaController)
            //mediaController.show(1)
            holder.videoView.start()
            holder.videoView.setOnPreparedListener {
                holder.videoProgressBar.visibility = View.GONE
                it.isLooping = true
            }


        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById(R.id.imageView) as ImageView
        var videoLayout: View = itemView.findViewById(R.id.videoLayout)
        var videoView: VideoView = itemView.findViewById(R.id.videoView)
        var videoProgressBar: ProgressBar = itemView.findViewById(R.id.videoProgressBar)


    }

    interface OnClickListener {
        fun onItemClicked(position: Int)
    }

    fun setOnCommentListener(onCommentClickListener: OnClickListener) {
        this.onItemClickListener = onCommentClickListener
    }
}
