package com.OnlineEvent.umangburman.event.HomeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.OnlineEvent.umangburman.event.MainActivity
import com.OnlineEvent.umangburman.event.R
import kotlinx.android.synthetic.main.first_fragment.*
import android.widget.ImageView


class HomeFragment : Fragment() {

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
        val imageList = intArrayOf(R.drawable.ic_attach_money_black_24dp, R.drawable.ic_launcher_background, com.OnlineEvent.umangburman.event.R.drawable.ic_attach_money_black_24dp)
        for (image in imageList) {
            setFlipperImage(image)
        }

    }

    private fun setFlipperImage(res: Int) {
        val image = ImageView(activity?.applicationContext)
        image.setBackgroundResource(res)
        flipper.addView(image)
        flipper.isAutoStart = true
        flipper.setFlipInterval(1000)
        flipper.startFlipping()
    }
}
