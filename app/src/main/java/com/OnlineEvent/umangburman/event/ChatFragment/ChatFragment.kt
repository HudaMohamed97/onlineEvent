package com.OnlineEvent.umangburman.event.ChatFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.OnlineEvent.umangburman.event.Adapter.ChatAdapter
import com.OnlineEvent.umangburman.event.Models.MessageModel
import com.OnlineEvent.umangburman.event.R
import com.bumptech.glide.Glide
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.chat_fragment.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException


class ChatFragment : Fragment() {
    private var root: View? = null
    private var eventId = 0
    lateinit var socket: Socket
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private val messageList = arrayListOf<MessageModel>()
    private lateinit var chatAdapter: ChatAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        return if (root != null) {
            NavHostFragment.findNavController(this).navigateUp()
            root
        } else {
            root = inflater.inflate(R.layout.chat_fragment, container, false)
            root
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventId = arguments?.getInt("EventId")!!
        setClickListeners()
        initSocket()
        initRecyclerView()
        setProfileImage()
        if (::socket.isInitialized && socket.connected()) {
            Log.i("hhhhh", " connected")
        } else {
            Log.i("hhhhh", " not connected")

        }
    }


    private fun initSocket() {
        val mOptions = IO.Options()
        // mOptions.query = "id=$receiveId"
        mOptions.query = "1"
        try {
            //  socket = IO.socket("http://160.153.246.213:5999", mOptions)
            socket = IO.socket("http://192.168.0.101:3000")
        } catch (e1: URISyntaxException) {
            e1.printStackTrace()
        }
        socket.on(Socket.EVENT_CONNECT, onConnect)
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
        socket.on("new message", onNewMessage)
        socket.connect()
        socket.emit("new message", "hidodo")

    }

    private val getMessagesResponse = Emitter.Listener { args ->
        (Runnable {
            val data = args[0] as JSONObject
            val result: JSONArray?
            try {
                result = data.getJSONArray("result")
                if (result != null) {
                    for (i in 0 until result.length()) {
                        //extract data from fired event
                        val message: String
                        val time: String
                        val fromUserId: Int
                        try {
                            message = result.getJSONObject(i).getString("message")
                            time = result.getJSONObject(i).getString("time")
                            fromUserId = result.getJSONObject(i).getInt("fromUserId")
                            /*messageList.add(
                                   *//* MessageModel(
                                             reciver.getName(),
                                             message,
                                             time,
                                             imageUrl,
                                             fromUserId
                                     ))*/
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            chatAdapter.notifyDataSetChanged()
            if (chatAdapter.itemCount > 0)
                recyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1)
        })
    }

    private fun scrollToBottom() {
        recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
    }

    private fun setClickListeners() {
        send.setOnClickListener {
            val messageToSend = message_editText.text.toString()
            if (messageToSend == "") {
                Toast.makeText(activity, "please Type message to send it,Thanks", Toast.LENGTH_SHORT).show()
            } else {
                messageList.add(MessageModel("huda", messageToSend))
                message_editText.setText("")
                chatAdapter.notifyItemInserted(messageList.size - 1)
                scrollToBottom()
                // emit message
            }

        }





        backButton.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()

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


        recyclerView = root!!.findViewById(R.id.message_list)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
    }


    private fun setProfileImage() {
        val photo = loginPreferences.getString("photo", "")
        Glide.with(context!!).load(photo).centerCrop()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile).into(imgProfile)
    }


    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        chatAdapter = ChatAdapter(messageList, 1)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = chatAdapter
    }


    private val onNewMessage = Emitter.Listener { args ->
        activity!!.runOnUiThread(Runnable {
            val data = args[0] as JSONObject
            val username: String
            val message: String
            try {
                username = data.getString("username")
                message = data.getString("message")
            } catch (e: JSONException) {
                return@Runnable
            }
            Toast.makeText(activity, "message$message", Toast.LENGTH_SHORT).show()

        })
    }

    private val onConnect = Emitter.Listener {
        activity?.runOnUiThread {
            Toast.makeText(activity,
                    "connect", Toast.LENGTH_LONG).show()

        }
    }


    private val onConnectError = Emitter.Listener {
        activity?.runOnUiThread {
            Log.i("hhhhhh", "Error connecting")
            Toast.makeText(activity, "not connected", Toast.LENGTH_LONG).show()
        }
    }
}