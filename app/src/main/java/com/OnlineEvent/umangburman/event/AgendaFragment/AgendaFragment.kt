package com.OnlineEvent.umangburman.event.AgendaFragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.OnlineEvent.umangburman.event.Adapter.AgendaAdapter
import com.OnlineEvent.umangburman.event.MainActivity
import com.OnlineEvent.umangburman.event.Models.AgendaDaysData
import com.OnlineEvent.umangburman.event.Models.Talks
import com.OnlineEvent.umangburman.event.R
import kotlinx.android.synthetic.main.agenda_fragment.*
import kotlinx.android.synthetic.main.speaker_fragment.descriptionTab

class AgendaFragment : Fragment() {
    private lateinit var root: View
    private var eventId = 0
    private lateinit var agendaViewModel: AgendaViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private val modelFeedArrayList = arrayListOf<AgendaDaysData>()
    private val agendaArrayList = arrayListOf<Talks>()
    private lateinit var agendaAdapter: AgendaAdapter
    var listDays = arrayListOf<Int>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).setDrawerLocked(false)
        (activity as MainActivity).showItem("second")
        agendaViewModel = ViewModelProviders.of(this).get(AgendaViewModel::class.java)
        root = inflater.inflate(R.layout.agenda_fragment, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventId = arguments?.getInt("EventId")!!
        setClickListeners()
        callAgendaData(eventId, false)
        initRecyclerView()
    }

    private fun setClickListeners() {
        daysSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parentView: AdapterView<*>,
                    selectedItemView: View,
                    position: Int,
                    id: Long
            ) {

                val selectedDay = modelFeedArrayList[position].id
                callAgenda(selectedDay)

            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }
        }


        modelFeedArrayList.clear()
        descriptionTab.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("EventId", eventId)
            NavHostFragment.findNavController(this).navigate(R.id.action_Event_ToDescription, bundle)

        }
        recyclerView = root.findViewById(R.id.agendaRecycler)
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
    }

    private fun callAgenda(selectedDay: Int) {
        agendaBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            agendaViewModel.getAgenda(selectedDay, accessToken)
        }
        agendaViewModel.getAgendaData().observe(this, Observer {
            agendaBar.visibility = View.GONE
            if (it != null) {
                agendaArrayList.clear()
                for (talk in it.data.talks) {
                    agendaArrayList.add(talk)
                }
                agendaAdapter.notifyDataSetChanged()

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun callAgendaData(eventId: Int, fromLoadMore: Boolean) {
        agendaProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            agendaViewModel.getAgendaDays(eventId, accessToken)
        }
        agendaViewModel.getAgendaDaysData().observe(this, Observer {
            agendaProgressBar.visibility = View.GONE
            if (it != null) {
                for (data in it.data) {
                    modelFeedArrayList.add(data)
                }
                intializeDaysSpinner()
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun intializeDaysSpinner() {
        for (i in 0 until (modelFeedArrayList.size)) {
            listDays.add(i+1)
        }
        val arrayAdapter =
                context?.let {
                    ArrayAdapter(
                            it,
                            R.layout.support_simple_spinner_dropdown_item,
                            listDays
                    )
                }
        arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (arrayAdapter != null) {
            daysSpinner.adapter = arrayAdapter
        }


    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        agendaAdapter = AgendaAdapter(agendaArrayList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = agendaAdapter
    }

}