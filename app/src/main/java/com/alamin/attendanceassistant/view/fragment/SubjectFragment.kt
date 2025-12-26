package com.alamin.attendanceassistant.view.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
import com.alamin.attendanceassistant.viewmodel.SubjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SubjectFragment"

@AndroidEntryPoint
class SubjectFragment : Fragment() {
    @Inject
    lateinit var subjectAdapter: SubjectAdapter

    @Inject
    lateinit var customAlertDialog: CustomAlertDialog

    @Inject
    lateinit var customOptionMenu: CustomOptionMenu

    private lateinit var binding: FragmentSubjectBinding
    private lateinit var viewModel: SubjectViewModel
    private val arg by navArgs<SubjectFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubjectBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this)[SubjectViewModel::class.java]


        binding.setOnAddSubjectClick {
                val action = SubjectFragmentDirections.actionSubjectFragmentToAddSubjectDialog(
                    arg.sectionId,
                    null
                )
                findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = subjectAdapter
        }

        lifecycleScope.launch {
            viewModel.message.collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewModel.getSubjectBySection(arg.sectionId)
                        .collectLatest { subjects ->
                            subjects?.let {
                                with(subjectAdapter) {
                                    setSubjectListener(object :
                                        ApplicationsCallBack.SetOnAdapterItemClickListener<Subject> {
                                        override fun onAdapterItemClick(dataClass: Subject) {
                                            val action =
                                                SubjectFragmentDirections.actionSubjectFragmentToAttendanceHolderFragment(
                                                    dataClass.subjectId
                                                )
                                            findNavController().navigate(action)
                                        }
                                    })

                                    setSubjectOptionListener(object :
                                        ApplicationsCallBack.SetOnAdapterOptionItemClickListener<Subject> {
                                        override fun onAdapterOptionItemClick(
                                            dataClass: Subject,
                                            view: View
                                        ) {
                                            customOptionMenu.showOptionMenu(
                                                view.context,
                                                view,
                                                object :
                                                    ApplicationsCallBack.SetOnOptionMenuClickListener {
                                                    override fun onEdit() {
                                                        val action =
                                                            SubjectFragmentDirections.actionSubjectFragmentToAddSubjectDialog(
                                                                0,
                                                                dataClass
                                                            )
                                                        findNavController().navigate(action)
                                                    }

                                                    override fun onDelete() {
                                                        customAlertDialog.createDialog("Warning !",
                                                            "Do You Want to Remove Subject ?",
                                                            R.color.theme,
                                                            object :
                                                                ApplicationsCallBack.SetOnAlertDialogClickListener {
                                                                override fun onPositive() {
                                                                    viewModel.deleteSubject(
                                                                        dataClass.subjectId
                                                                    )
                                                                    viewModel.deleteAttendanceBySubject(
                                                                        dataClass.subjectId
                                                                    )
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

                                                })
                                        }


                                    })

                                    setDiffUtils(ArrayList(it))

                                }
                            }
                        }
                }
            }

        return binding.root
    }

}