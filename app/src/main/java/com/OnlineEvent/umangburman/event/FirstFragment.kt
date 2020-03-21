package com.OnlineEvent.umangburman.event

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as MainActivity).setDrawerLocked(false)
        (activity as MainActivity).showItem("second")
        return inflater.inflate(R.layout.first_fragment, container, false)
    }
}
