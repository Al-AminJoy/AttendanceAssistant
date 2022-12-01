package com.alamin.attendanceassistant.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alamin.attendanceassistant.databinding.FragmentAttendanceHolderBinding
import com.alamin.attendanceassistant.model.data.Subject
import com.alamin.attendanceassistant.view.adapter.ViewPagerAdapter
import com.alamin.attendanceassistant.view_model.SubjectViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

private const val TAG = "AttendanceHolderFragment"

@AndroidEntryPoint
class AttendanceHolderFragment : Fragment() {

    @Inject
    lateinit var attendanceFragment: AttendanceFragment
    @Inject
    lateinit var reportFragment: StudentReportFragment
    @Inject
    lateinit var studentListFragment: StudentListFragment
    @Inject
    lateinit var viewPagerAdapter: ViewPagerAdapter

    private lateinit var binding: FragmentAttendanceHolderBinding
    private val arg by navArgs<AttendanceHolderFragmentArgs>()
    private lateinit var subjectViewModel: SubjectViewModel

    private lateinit var subject: Subject


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAttendanceHolderBinding.inflate(layoutInflater)

        subjectViewModel = ViewModelProvider(this)[SubjectViewModel::class.java]

        lifecycleScope.launchWhenCreated {
            subjectViewModel.getSubjectById(arg.subject.subjectId).collectLatest {
                it?.let {
                    subject = it
                    Log.d(TAG, "onCreateView: $subject")

                    attendanceFragment.setSubject(subject)
                    reportFragment.setSubject(subject)
                    studentListFragment.setSubject(subject)
                }
            }
        }

        binding.setOnAddStudent {
            val action =
                AttendanceHolderFragmentDirections.actionAttendanceHolderFragmentToAddStudentDialog(subject,null)
            findNavController().navigate(action)
        }

        setupViewPager()

        return binding.root
    }

    private fun setupViewPager() {


        val fragmentList = arrayListOf(attendanceFragment,reportFragment,studentListFragment)
        val fragmentTitleList = arrayListOf("Attendance","Report","Students")

        viewPagerAdapter.addFragment(fragmentList, fragmentTitleList)

        binding.pager.apply {
            adapter = viewPagerAdapter
            currentItem = 0
        }

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = viewPagerAdapter.getTabTitle(position)
        }.attach()    }
}