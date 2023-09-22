package com.example.movieapp.view.tabLayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentPageAdapter(
    fa: FragmentActivity
) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> NowPlayingFragment()
            1-> UpcomingFragment()
            2-> TopRatedFragment()
            else->NowPlayingFragment()
        }
    }
}