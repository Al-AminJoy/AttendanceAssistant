package com.alamin.attendanceassistant.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alamin.attendanceassistant.R
import com.alamin.attendanceassistant.databinding.FragmentAttendanceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttendanceFragment : Fragment() {
    private lateinit var binding: FragmentAttendanceBinding
    private val arg by navArgs<AttendanceFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAttendanceBinding.inflate(layoutInflater)

        binding.setOnAddStudent {
            val action  = AttendanceFragmentDirections.actionAttendanceFragmentToAddStudentDialog(arg.subject)
            findNavController().navigate(action)
        }

        return binding.root
    }

}