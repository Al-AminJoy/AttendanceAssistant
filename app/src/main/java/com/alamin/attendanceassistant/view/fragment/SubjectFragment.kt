package com.alamin.attendanceassistant.view.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.alamin.attendanceassistant.R
import com.alamin.attendanceassistant.databinding.FragmentSubjectBinding
import com.alamin.attendanceassistant.model.data.Subject
import com.alamin.attendanceassistant.utils.ApplicationsCallBack
import com.alamin.attendanceassistant.utils.CustomAlertDialog
import com.alamin.attendanceassistant.utils.CustomOptionMenu
import com.alamin.attendanceassistant.view.adapter.SubjectAdapter
import com.alamin.attendanceassistant.view_model.AttendanceViewModel
import com.alamin.attendanceassistant.view_model.SubjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class SubjectFragment : Fragment() {
    @Inject
    lateinit var subjectAdapter: SubjectAdapter
    @Inject
    lateinit var customAlertDialog: CustomAlertDialog
    @Inject
    lateinit var customOptionMenu: CustomOptionMenu

    private lateinit var binding: FragmentSubjectBinding
    private lateinit var subjectViewModel: SubjectViewModel
    private lateinit var attendanceViewModel: AttendanceViewModel
    private val arg by navArgs<SubjectFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubjectBinding.inflate(layoutInflater)

        subjectViewModel = ViewModelProvider(this)[SubjectViewModel::class.java]
        attendanceViewModel = ViewModelProvider(this)[AttendanceViewModel::class.java]

        binding.setOnAddSubjectClick {
            val action = SubjectFragmentDirections.actionSubjectFragmentToAddSubjectDialog(arg.section)
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = subjectAdapter
        }

        lifecycleScope.launchWhenCreated {
            subjectViewModel.message.collect{
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launchWhenCreated {
            subjectViewModel.getSubjectBySection(arg.section.sectionId).collectLatest {
                it?.let {
                    with(subjectAdapter){
                        setDiffUtils(ArrayList(it))
                        setSubjectListener(object : ApplicationsCallBack.SetOnAdapterItemClickListener<Subject>{
                            override fun onAdapterItemClick(dataClass: Subject) {
                                val action = SubjectFragmentDirections.actionSubjectFragmentToAttendanceHolderFragment(dataClass)
                                findNavController().navigate(action)
                            }
                        })

                        setSubjectOptionListener(object : ApplicationsCallBack.SetOnAdapterOptionItemClickListener<Subject>{
                            override fun onAdapterOptionItemClick(dataClass: Subject, view: View) {
                              customOptionMenu.showOptionMenu(view.context,view,object : ApplicationsCallBack.SetOnOptionMenuClickListener{
                                  override fun onEdit() {

                                  }

                                  override fun onDelete() {
                                      customAlertDialog.createDialog("Warning !",
                                          "Do You Want to Remove Subject ?",
                                          R.color.theme,
                                          object : ApplicationsCallBack.SetOnAlertDialogClickListener{
                                              override fun onPositive() {
                                                  subjectViewModel.deleteSubject(dataClass.subjectId)
                                                  attendanceViewModel.deleteAttendanceBySubject(dataClass.subjectId)
                                              }

                                              override fun onNegative() {
                                                  Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show()
                                              }

                                          })
                                  }

                              })
                            }


                        })
                    }
                }
            }
        }

        return binding.root
    }

}