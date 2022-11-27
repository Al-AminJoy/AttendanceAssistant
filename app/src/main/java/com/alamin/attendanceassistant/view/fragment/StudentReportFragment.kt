package com.alamin.attendanceassistant.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alamin.attendanceassistant.R
import com.alamin.attendanceassistant.databinding.FragmentAttendanceHolderBinding
import com.alamin.attendanceassistant.databinding.FragmentStudentReportBinding
import com.alamin.attendanceassistant.view.adapter.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "StudentReportFragment"
@AndroidEntryPoint
class StudentReportFragment @Inject constructor() : Fragment() {
    private lateinit var binding: FragmentStudentReportBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStudentReportBinding.inflate(layoutInflater)

        return binding.root
    }

}