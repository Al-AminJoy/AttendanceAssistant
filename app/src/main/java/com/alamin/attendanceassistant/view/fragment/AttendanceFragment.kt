package com.alamin.attendanceassistant.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alamin.attendanceassistant.databinding.FragmentAttendanceBinding
import com.alamin.attendanceassistant.view_model.AttendanceViewModel
import com.alamin.attendanceassistant.view_model.SubjectViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttendanceFragment : Fragment() {
    private lateinit var binding: FragmentAttendanceBinding
    private lateinit var subjectViewModel: SubjectViewModel
    lateinit var attendanceViewModel: AttendanceViewModel
    private val arg by navArgs<AttendanceFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAttendanceBinding.inflate(layoutInflater)

        subjectViewModel = ViewModelProvider(this)[SubjectViewModel::class.java]
        attendanceViewModel = ViewModelProvider(this)[AttendanceViewModel::class.java]

        binding.setOnAddStudent {
            val action  = AttendanceFragmentDirections.actionAttendanceFragmentToAddStudentDialog(arg.subject)
            findNavController().navigate(action)
        }

        return binding.root
    }

}