package com.OnlineEvent.umangburman.event.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.OnlineEvent.umangburman.event.Adapter.ChatAdapter.MyViewHolder

import com.OnlineEvent.umangburman.event.Models.MessageModel
import com.OnlineEvent.umangburman.event.R


class ChatAdapter(private val MessageList: List<MessageModel>,  var userId: Int) : RecyclerView.Adapter<MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var myMessage: TextView = view.findViewById(R.id.tv_my_msg)
        var userMessage: TextView = view.findViewById(R.id.tv_other_msg)
        val myMessageContainer: LinearLayout = view.findViewById(R.id.container_my_msg)
        val userMessageContainer: LinearLayout = view.findViewById(R.id.container_other_msg)

    }

    override fun getItemCount(): Int {
        return MessageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val (_, message, _, _, _, fromUserId) = MessageList[position]
        if (userId == fromUserId) {
            holder.myMessageContainer.visibility = View.VISIBLE
            holder.userMessageContainer.visibility = View.GONE
            if (message!!.isNotEmpty()) {
                holder.myMessage.text = message
            }
        } else {
            holder.myMessageContainer.visibility = View.GONE
            holder.userMessageContainer.visibility = View.VISIBLE
            if (message!!.isNotEmpty()) {
                holder.userMessage.text = message
            }
        }

    }
}