package com.alamin.attendanceassistant.view.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alamin.attendanceassistant.R
import com.alamin.attendanceassistant.databinding.FragmentAttendanceBinding
import com.alamin.attendanceassistant.model.data.Attendance
import com.alamin.attendanceassistant.model.data.StudentAttendance
import com.alamin.attendanceassistant.model.data.StudentAttendanceHolder
import com.alamin.attendanceassistant.model.data.Subject
import com.alamin.attendanceassistant.utils.AppUtils
import com.alamin.attendanceassistant.utils.ApplicationsCallBack
import com.alamin.attendanceassistant.view.adapter.AttendanceAdapter
import com.alamin.attendanceassistant.view_model.AttendanceViewModel
import com.alamin.attendanceassistant.view_model.SubjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

private const val TAG = "AttendanceFragment"

@AndroidEntryPoint
class AttendanceFragment @Inject constructor() : Fragment() {
    @Inject
    lateinit var attendanceAdapter: AttendanceAdapter

    private lateinit var binding: FragmentAttendanceBinding
    private lateinit var subjectViewModel: SubjectViewModel
    private lateinit var attendanceViewModel: AttendanceViewModel
    private lateinit var subject: Subject
    private var studentAttendanceList = arrayListOf<StudentAttendance>()
    private lateinit var calendar: Calendar
    private var attendanceId: String = ""

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAttendanceBinding.inflate(layoutInflater)

        binding.hasDateChosen = false

        subjectViewModel = ViewModelProvider(this)[SubjectViewModel::class.java]
        attendanceViewModel = ViewModelProvider(this)[AttendanceViewModel::class.java]
        calendar = Calendar.getInstance()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = attendanceAdapter
        }


        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                attendanceId = "${subject.subjectId}_${calendar.get(Calendar.DAY_OF_MONTH)}-${
                    calendar.get(Calendar.MONTH)
                }-${
                    calendar.get(Calendar.YEAR)
                }"

                binding.btnSelectDate.text =
                    "${calendar.get(Calendar.DAY_OF_MONTH)}-${calendar.get(Calendar.MONTH)}-${
                        calendar.get(Calendar.YEAR)
                    }"
                binding.hasDateChosen = true
                getAttendance()
            }


        binding.setOnDateSelect {
            if (subject.studentHolder.studentList.isEmpty()){
                Toast.makeText(requireContext(), "No Student Found", Toast.LENGTH_SHORT).show()
                return@setOnDateSelect
            }
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
            datePickerDialog.datePicker.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
        }

        lifecycleScope.launchWhenCreated {
            attendanceViewModel.studentAttendanceFlowList.collectLatest {
                it?.let {
                    with(attendanceAdapter) {
                        studentAttendanceList.clear()
                        studentAttendanceList = ArrayList(it)
                        setAdapterItemClickListener(object :
                            ApplicationsCallBack.SetOnAttendanceClickListener<StudentAttendance> {
                            override fun onAdapterItemClick(
                                dataClass: StudentAttendance,
                                isPresent: Boolean
                            ) {
                                val listIndex = studentAttendanceList.indexOf(dataClass)
                                dataClass.isPresent = isPresent
                                studentAttendanceList[listIndex] = dataClass
                            }
                        })

                        setDiffUtils(ArrayList(studentAttendanceList.sortedBy { studentAttendance -> studentAttendance.studentId }))

                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            attendanceViewModel.message.collect {
                binding.hasDateChosen = it.lowercase() != "Success".lowercase()
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }


        binding.setOnAttendanceSubmit {
            attendanceViewModel.createAttendance(
                attendanceId,
                studentAttendanceList,
                subject.subjectId
            )
        }



        return binding.root
    }

    private fun getAttendance() {
        lifecycleScope.launchWhenCreated {
            attendanceViewModel.getAttendanceById(attendanceId)
                .collectLatest {
                    if (it == null) {
                        attendanceViewModel.getStudentListOfAttendance(arrayListOf(), subject)
                    } else {
                        attendanceViewModel.getStudentListOfAttendance(
                            ArrayList(it.studentAttendanceHolder.studentAttendanceList),
                            subject
                        )
                    }
                }
        }
    }

    fun setSubject(subject: Subject){
        this.subject = subject
    }

}