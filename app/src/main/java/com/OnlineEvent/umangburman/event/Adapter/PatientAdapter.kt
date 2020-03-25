package com.OnlineEvent.umangburman.event.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.OnlineEvent.umangburman.event.Models.PatientResponseData
import com.OnlineEvent.umangburman.event.R
import java.util.*


class PatientAdapter(modelFeedArrayList: ArrayList<PatientResponseData>) :
    RecyclerView.Adapter<PatientAdapter.MyViewHolder>() {

   // lateinit var onItemClickListener: OnCommentClickListener
    private var context: Context? = null
    private var fromTab = ""


    override fun getItemCount(): Int {
        return modelFeedArrayList.size
    }

    var modelFeedArrayList = ArrayList<PatientResponseData>()


    init {
        this.modelFeedArrayList = modelFeedArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_row, parent, false)
        context = parent.context
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val modelFeed = modelFeedArrayList[position]
      /*  holder.tvName.text = "hospital"
         //   modelFeed.hospital?.name
        holder.doctorName.text = modelFeed.name
        holder.time.text = modelFeed.created_at*/






    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

      /*  var dotsImage:ImageView = itemView.findViewById(R.id.textViewOptions)
        var tvName: TextView = itemView.findViewById<View>(R.id.hospitalName) as TextView*/


    }

   /* interface OnCommentClickListener {
        fun onDotsImageClicked(position: Int, fromTab: String)
    }

    fun setOnCommentListener(onCommentClickListener: OnCommentClickListener) {
        this.onItemClickListener = onCommentClickListener
    }
*/
}
