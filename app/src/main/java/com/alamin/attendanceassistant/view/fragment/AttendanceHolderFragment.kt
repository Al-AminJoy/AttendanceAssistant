package com.alamin.attendanceassistant.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.alamin.attendanceassistant.databinding.FragmentAttendanceHolderBinding
import com.alamin.attendanceassistant.view.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "AttendanceHolderFragment"

@AndroidEntryPoint
class AttendanceHolderFragment : Fragment() {

    @Inject
    lateinit var attendanceFragment: AttendanceFragment
    @Inject
    lateinit var reportFragment: StudentReportFragment
    @Inject
    lateinit var viewPagerAdapter: ViewPagerAdapter

    private lateinit var binding: FragmentAttendanceHolderBinding
    private val arg by navArgs<AttendanceHolderFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAttendanceHolderBinding.inflate(layoutInflater)

        setupViewPager()

        return binding.root
    }

    private fun setupViewPager() {
        attendanceFragment.setSubject(arg.subject.subjectId)
        reportFragment.setSubject(arg.subject.subjectId)
        val fragmentList = arrayListOf(attendanceFragment,reportFragment)
        val fragmentTitleList = arrayListOf("Attendance","Report")

        viewPagerAdapter.addFragment(fragmentList, fragmentTitleList)

        binding.pager.apply {
            adapter = viewPagerAdapter
            currentItem = 0
        }

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = viewPagerAdapter.getTabTitle(position)
        }.attach()    }
}