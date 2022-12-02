package com.alamin.attendanceassistant.view.fragment

import android.R
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.alamin.attendanceassistant.databinding.FragmentSubjectBinding
import com.alamin.attendanceassistant.model.data.Subject
import com.alamin.attendanceassistant.utils.ApplicationsCallBack
import com.alamin.attendanceassistant.view.adapter.SubjectAdapter
import com.alamin.attendanceassistant.view_model.SubjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class SubjectFragment : Fragment() {
    @Inject
    lateinit var subjectAdapter: SubjectAdapter
    private lateinit var binding: FragmentSubjectBinding
    private lateinit var subjectViewModel: SubjectViewModel
    private val arg by navArgs<SubjectFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubjectBinding.inflate(layoutInflater)

        subjectViewModel = ViewModelProvider(this)[SubjectViewModel::class.java]

        binding.setOnAddSubjectClick {
            val action = SubjectFragmentDirections.actionSubjectFragmentToAddSubjectDialog(arg.section)
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = subjectAdapter
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
                                val popupMenu= PopupMenu(view.context,view)
                                with(popupMenu){
                                    inflate(com.alamin.attendanceassistant.R.menu.subject_options_menu)
                                    setOnMenuItemClickListener {item->

                                        when(item.itemId) {
                                            com.alamin.attendanceassistant.R.id.btnEdit->{

                                            }
                                            com.alamin.attendanceassistant.R.id.btnDelete->{

                                            }
                                        }
                                        true
                                    }
                                    show()
                                }
                            }


                        })
                    }
                }
            }
        }

        return binding.root
    }

}