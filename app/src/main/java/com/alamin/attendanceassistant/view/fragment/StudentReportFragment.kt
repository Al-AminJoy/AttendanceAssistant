package com.alamin.attendanceassistant.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.alamin.attendanceassistant.R
import com.alamin.attendanceassistant.databinding.FragmentAttendanceHolderBinding
import com.alamin.attendanceassistant.databinding.FragmentStudentReportBinding
import com.alamin.attendanceassistant.model.data.Attendance
import com.alamin.attendanceassistant.model.data.StudentAttendance
import com.alamin.attendanceassistant.view.adapter.ViewPagerAdapter
import com.alamin.attendanceassistant.view_model.AttendanceViewModel
import com.alamin.attendanceassistant.view_model.SubjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

private const val TAG = "StudentReportFragment"

@AndroidEntryPoint
class StudentReportFragment @Inject constructor() : Fragment() {

    private lateinit var binding: FragmentStudentReportBinding
    private var subjectId: Int = 0
    private lateinit var subjectViewModel: SubjectViewModel
    private lateinit var attendanceViewModel: AttendanceViewModel
    private var studentList = ArrayList<String>()
    private var studentAttendanceList = arrayListOf<Attendance>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStudentReportBinding.inflate(layoutInflater)

        binding.totalClass = 0
        binding.presentClass = 0
        binding.absentClass = 0
        binding.presentPercentage = 0.00

        subjectViewModel = ViewModelProvider(this)[SubjectViewModel::class.java]
        attendanceViewModel = ViewModelProvider(this)[AttendanceViewModel::class.java]

        binding.setOnStudentClick {
            binding.txtStudent.showDropDown()
        }

        binding.txtStudent.setOnItemClickListener { adapterView, view, position, id ->

            val data = studentList[position].split(".")
            val selectedStudent = data[0].toInt()

            attendanceViewModel.calculateAttendance(selectedStudent,studentAttendanceList)

        }

        lifecycleScope.launchWhenCreated {
            attendanceViewModel.present.collectLatest {
                it.let {
                    val totalClass = studentAttendanceList.size
                    binding.totalClass = totalClass
                    binding.presentClass = it
                    binding.absentClass = totalClass - it
                    binding.presentPercentage = Math.round(((it.toDouble()/totalClass.toDouble())*100)*100.0)/100.0
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            attendanceViewModel.getAttendanceBySubject(subjectId).collectLatest { 
                it?.let {
                    studentAttendanceList.clear()
                    studentAttendanceList.addAll(it)
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            subjectViewModel.getSubjectById(subjectId).collectLatest {
                it?.let {
                    studentList = ArrayList(it.studentHolder.studentList
                        .sortedBy { std -> std.studentId }
                        .map { student -> "${student.studentId}. ${student.studentName}" })

                    var studentAdapter = ArrayAdapter(requireContext(),R.layout.row_student_dropdown,R.id.txtStudent,studentList)
                    binding.txtStudent.setAdapter(studentAdapter)
                }
            }
        }

        return binding.root
    }

    fun setSubject(subjectId: Int){
        this.subjectId = subjectId
    }

}