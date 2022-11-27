package com.alamin.attendanceassistant.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import javax.inject.Inject

class ViewPagerAdapter @Inject constructor(activity: FragmentActivity): FragmentStateAdapter(activity) {

    private val fragmentList: MutableList<Fragment> = ArrayList()
    private val fragmentTitleList: MutableList<String> = ArrayList()

     fun getTabTitle(position : Int): String{
        return fragmentTitleList[position]
    }

    fun addFragment(fragment: List<Fragment>, title: List<String>) {
        fragmentList.addAll(fragment)
        fragmentTitleList.addAll(title)
    }


    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}