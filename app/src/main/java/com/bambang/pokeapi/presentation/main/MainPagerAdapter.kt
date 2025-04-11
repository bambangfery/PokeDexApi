package com.bambang.pokeapi.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bambang.pokeapi.presentation.main.account.AccountFragment
import com.bambang.pokeapi.presentation.main.home.HomeFragment

class MainPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> AccountFragment()
            else -> Fragment()
        }
    }
}