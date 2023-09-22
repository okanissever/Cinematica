package com.example.movieapp.view.detailsfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsPageAdapter(
    fa: FragmentActivity,
    private val movieId: Int
) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = when(position){
            0-> Overviewfragment()
            1-> ActorFragment()
            2-> ReviewFragment()
            else-> Overviewfragment()
        }

        val args = Bundle()
        args.putInt("movieId", movieId)
        fragment.arguments = args

        return fragment
    }

}
