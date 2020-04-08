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
import com.OnlineEvent.umangburman.event.Models.AgendaDaysData
import com.OnlineEvent.umangburman.event.Models.AgendaModelResponse
import com.OnlineEvent.umangburman.event.Models.SpeakerData
import com.OnlineEvent.umangburman.event.Models.Talks
import com.OnlineEvent.umangburman.event.Models.scheduleModels.ScheduleResponse
import com.OnlineEvent.umangburman.event.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.about_fragment.*
import kotlinx.android.synthetic.main.speaker_profile.view.*
import java.util.*


class AgendaAdapter(modelFeedArrayList: ArrayList<Talks>) :
        RecyclerView.Adapter<AgendaAdapter.MyViewHolder>() {

    private var context: Context? = null


    override fun getItemCount(): Int {
        return modelFeedArrayList.size
    }

    var modelFeedArrayList = ArrayList<Talks>()


    init {
        this.modelFeedArrayList = modelFeedArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.agenda_row, parent, false)
        context = parent.context
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val modelFeed = modelFeedArrayList[position]

        holder.dateFrom.text = modelFeed.time_from
        holder.dateTo.text = modelFeed.time_to
        holder.sessionTopic.text = modelFeed.title
        if (modelFeed.speakers.isNotEmpty()) {
            for (speaker in modelFeed.speakers) {
                holder.speakersAgenda.append(speaker.name + " ")
            }
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dateFrom: TextView = itemView.findViewById<View>(R.id.dateFrom) as TextView
        var dateTo: TextView = itemView.findViewById(R.id.dateTo)
        var sessionTopic: TextView = itemView.findViewById(R.id.sessionTopic)
        var speakersAgenda: TextView = itemView.findViewById(R.id.speakersAgenda)


    }

   /* interface OnClickListener {
        fun onItemClicked(position: Int)
    }

    fun setOnCommentListener(onCommentClickListener: OnClickListener) {
        this.onItemClickListener = onCommentClickListener
    }*/
}
