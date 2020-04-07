package com.OnlineEvent.umangburman.event.Adapter

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.OnlineEvent.umangburman.event.Models.SpeakerData
import com.OnlineEvent.umangburman.event.Models.scheduleModels.ScheduleResponse
import com.OnlineEvent.umangburman.event.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.about_fragment.*
import java.util.*


class SpeakerAdapter(modelFeedArrayList: ArrayList<SpeakerData>) :
        RecyclerView.Adapter<SpeakerAdapter.MyViewHolder>() {

    private var context: Context? = null
    lateinit var onItemClickListener: OnClickListener


    override fun getItemCount(): Int {
        return modelFeedArrayList.size
    }

    var modelFeedArrayList = ArrayList<SpeakerData>()


    init {
        this.modelFeedArrayList = modelFeedArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.speaker_row, parent, false)
        context = parent.context
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val modelFeed = modelFeedArrayList[position]

        holder.speakerName.text = modelFeed.name
        Glide.with(context!!).load(modelFeed .photo).centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile).into(holder.speakerImage)

        holder.itemView.setOnClickListener{
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(position)
            }
        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var speakerName: TextView = itemView.findViewById<View>(R.id.speakerName) as TextView
        var speakerImage: ImageView = itemView.findViewById(R.id.imgProfile)



    }

    interface OnClickListener {
        fun onItemClicked(position: Int)
    }

    fun setOnCommentListener(onCommentClickListener: OnClickListener) {
        this.onItemClickListener = onCommentClickListener
    }
}
