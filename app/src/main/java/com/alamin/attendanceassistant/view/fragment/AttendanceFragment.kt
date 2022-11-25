package com.alamin.attendanceassistant.view.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alamin.attendanceassistant.R
import com.alamin.attendanceassistant.databinding.FragmentAttendanceBinding
import com.alamin.attendanceassistant.model.data.Student
import com.alamin.attendanceassistant.model.data.StudentAttendance
import com.alamin.attendanceassistant.model.data.Subject
import com.alamin.attendanceassistant.utils.AppUtils
import com.alamin.attendanceassistant.view.adapter.AttendanceAdapter
import com.alamin.attendanceassistant.view_model.AttendanceViewModel
import com.alamin.attendanceassistant.view_model.SubjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AttendanceFragment : Fragment() {
    @Inject
    lateinit var attendanceAdapter: AttendanceAdapter

    private lateinit var binding: FragmentAttendanceBinding
    private lateinit var subjectViewModel: SubjectViewModel
    private lateinit var attendanceViewModel: AttendanceViewModel
    private val arg by navArgs<AttendanceFragmentArgs>()
    private var studentAttendanceList = arrayListOf<StudentAttendance>()
    private lateinit var subject: Subject
    private lateinit var calendar: Calendar
    private var attendanceDate: Long = 0L

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAttendanceBinding.inflate(layoutInflater)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = attendanceAdapter
        }

        calendar = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                attendanceDate = calendar.timeInMillis
                binding.btnSelectDate.text =
                    "${calendar.get(Calendar.DAY_OF_MONTH)}-${calendar.get(Calendar.MONTH)}-${
                        calendar.get(Calendar.YEAR)
                    }"
                getAttendance()
            }


        binding.setOnDateSelect {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
            datePickerDialog.datePicker.minDate =
                System.currentTimeMillis() - (AppUtils.ONE_DAY_IN_MILLISECOND * 6)
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.theme, null))
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.theme, null))
        }


        subjectViewModel = ViewModelProvider(this)[SubjectViewModel::class.java]
        attendanceViewModel = ViewModelProvider(this)[AttendanceViewModel::class.java]

        lifecycleScope.launchWhenCreated {
            subjectViewModel.getSubjectById(arg.subject.subjectId).collectLatest {
                it?.let {
                    subject = it
                    //attendanceAdapter.setDiffUtils(studentAttendanceList)
                }
            }
        }





        binding.setOnAddStudent {
            val action =
                AttendanceFragmentDirections.actionAttendanceFragmentToAddStudentDialog(subject)
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun getAttendance() {
        lifecycleScope.launchWhenCreated {
            attendanceViewModel.getAttendanceBySubjectAndDate(subject.subjectId, attendanceDate)
                .collectLatest {
                    studentAttendanceList.clear()

                    if (it == null || it.studentAttendanceHolder.studentAttendanceList.isEmpty()) {
                        if (subject.studentHolder.studentList.isNotEmpty()) {
                            for (student in subject.studentHolder.studentList) {
                                val studentAttendance =
                                    StudentAttendance(student.studentId, student.studentName)
                                studentAttendanceList.add(studentAttendance)
                            }
                        }
                    } else {
                        studentAttendanceList.addAll(it.studentAttendanceHolder.studentAttendanceList)

                    }

                    attendanceAdapter.setDiffUtils(studentAttendanceList)

                }
        }
    }

}