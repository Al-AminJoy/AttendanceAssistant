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
import androidx.recyclerview.widget.LinearLayoutManager
import com.alamin.attendanceassistant.R
import com.alamin.attendanceassistant.databinding.FragmentStudentListBinding
import com.alamin.attendanceassistant.model.data.Student
import com.alamin.attendanceassistant.utils.ApplicationsCallBack
import com.alamin.attendanceassistant.view.adapter.StudentAdapter
import com.alamin.attendanceassistant.view_model.StudentViewModel
import com.alamin.attendanceassistant.view_model.SubjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

private const val TAG = "StudentListFragment"

@AndroidEntryPoint
class StudentListFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var studentAdapter: StudentAdapter

    private lateinit var binding: FragmentStudentListBinding

    private lateinit var subjectViewModel: SubjectViewModel

    private var subjectId : Int = 0

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
            subjectViewModel.getSubjectById(subjectId).collectLatest {
                it?.let {
                    with(studentAdapter){
                        setStudentDiffUtils(ArrayList(it.studentHolder.studentList))
                        setAdapterClickListener(object : ApplicationsCallBack.SetOnStudentClickListener<Student>{
                            override fun onAdapterItemClick(dataClass: Student, isUpdate: Boolean) {
                                Log.d(TAG, "onAdapterItemClick: $dataClass $isUpdate")
                                if(isUpdate){
                                    val action = AttendanceHolderFragmentDirections.actionAttendanceHolderFragmentToAddStudentDialog(it,dataClass)
                                    findNavController().navigate(action)
                                }
                            }

                        })
                    }
                }
            }
        }

        return binding.root
    }

    fun setSubject(subjectId:Int){
        this.subjectId = subjectId
    }}