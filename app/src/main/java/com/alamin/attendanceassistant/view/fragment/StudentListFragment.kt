package com.alamin.attendanceassistant.view.fragment

import android.app.AlertDialog
import android.content.DialogInterface
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.alamin.attendanceassistant.R
import com.alamin.attendanceassistant.databinding.FragmentStudentListBinding
import com.alamin.attendanceassistant.model.data.Student
import com.alamin.attendanceassistant.model.data.Subject
import com.alamin.attendanceassistant.utils.ApplicationsCallBack
import com.alamin.attendanceassistant.utils.CustomAlertDialog
import com.alamin.attendanceassistant.view.adapter.StudentAdapter
import com.alamin.attendanceassistant.view_model.StudentViewModel
import com.alamin.attendanceassistant.view_model.SubjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

private const val TAG = "StudentListFragment"

@AndroidEntryPoint
class StudentListFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var studentAdapter: StudentAdapter

    @Inject
    lateinit var customAlertDialog: CustomAlertDialog

    private lateinit var binding: FragmentStudentListBinding

    private lateinit var subjectViewModel: SubjectViewModel

    private lateinit var subject: Subject

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentListBinding.inflate(layoutInflater)

        subjectViewModel = ViewModelProvider(this)[SubjectViewModel::class.java]

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = studentAdapter
        }

        lifecycleScope.launchWhenCreated {
            subjectViewModel.getSubjectById(subject.subjectId).collectLatest {
                it?.let {
                    with(studentAdapter) {
                        setStudentDiffUtils(ArrayList(it.studentHolder.studentList.sortedBy { it.studentId }))
                        setAdapterClickListener(object :
                            ApplicationsCallBack.SetOnStudentClickListener<Student> {
                            override fun onAdapterItemClick(dataClass: Student, isUpdate: Boolean) {
                                if (isUpdate) {
                                    val action =
                                        AttendanceHolderFragmentDirections.actionAttendanceHolderFragmentToAddStudentDialog(
                                            it,
                                            dataClass
                                        )
                                    findNavController().navigate(action)
                                } else {
                                    customAlertDialog.createDialog("Warning!",
                                        "Do You Want to Remove ?",
                                        R.color.theme,
                                        object :
                                            ApplicationsCallBack.SetOnAlertDialogClickListener {
                                            override fun onPositive() {
                                                subjectViewModel.removeStudent(dataClass, subject)
                                            }

                                            override fun onNegative() {
                                                Toast.makeText(
                                                    requireContext(),
                                                    "Cancelled",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }

                                        })
                                }
                            }

                        })
                    }
                }
            }
        }

        return binding.root
    }

    fun setSubject(subject: Subject) {
        this.subject = subject
        if (studentAdapter != null) {
            studentAdapter.setStudentDiffUtils(ArrayList(subject.studentHolder.studentList.sortedBy { it.studentId }))
        }
    }
}