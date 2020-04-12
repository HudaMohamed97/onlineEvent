package com.OnlineEvent.umangburman.event.Adapter

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.OnlineEvent.umangburman.event.Models.scheduleModels.ScheduleResponse
import com.OnlineEvent.umangburman.event.R
import java.util.*


class ScheduleAdapter(modelFeedArrayList: ArrayList<ScheduleResponse>) :
        RecyclerView.Adapter<ScheduleAdapter.MyViewHolder>() {

    private var context: Context? = null
    lateinit var onItemClickListener: OnClickListener


    override fun getItemCount(): Int {
        return modelFeedArrayList.size
    }

    var modelFeedArrayList = ArrayList<ScheduleResponse>()


    init {
        this.modelFeedArrayList = modelFeedArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.schedule_row_list, parent, false)
        context = parent.context
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val modelFeed = modelFeedArrayList[position]
        holder.eventDescription.movementMethod = ScrollingMovementMethod()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.eventDescription.text = Html.fromHtml(modelFeed.description, Html.FROM_HTML_MODE_COMPACT)
        } else {
            holder.eventDescription.text = Html.fromHtml(modelFeed.description)
        }
        holder.date.text = modelFeed.start_date
        val start = modelFeed.start_date
        val end = modelFeed.end_date
        holder.time.text = "$start $end"
        holder.EventName.text = modelFeed.name
        holder.moreButton.setOnClickListener {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(position)
            }
        }
        holder.register_Button.setOnClickListener {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onRegisterEventClicked(position)
            }
        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date: TextView = itemView.findViewById<View>(R.id.date) as TextView
        var eventDescription: TextView = itemView.findViewById<View>(R.id.eventDescription) as TextView
        var EventName: TextView = itemView.findViewById<View>(R.id.EventName) as TextView
        var time: TextView = itemView.findViewById<View>(R.id.time) as TextView
        var moreButton: Button = itemView.findViewById(R.id.learnMoreButton)
        var register_Button: Button = itemView.findViewById(R.id.register_Button)


    }

    interface OnClickListener {
        fun onItemClicked(position: Int)
        fun onRegisterEventClicked(position: Int)
    }

    fun setOnCommentListener(onCommentClickListener: OnClickListener) {
        this.onItemClickListener = onCommentClickListener
    }
}
