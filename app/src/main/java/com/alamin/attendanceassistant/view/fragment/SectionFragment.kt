package com.alamin.attendanceassistant.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.alamin.attendanceassistant.R
import com.alamin.attendanceassistant.databinding.FragmentSectionBinding
import com.alamin.attendanceassistant.model.data.Section
import com.alamin.attendanceassistant.utils.ApplicationsCallBack
import com.alamin.attendanceassistant.utils.CustomAlertDialog
import com.alamin.attendanceassistant.utils.CustomOptionMenu
import com.alamin.attendanceassistant.view.adapter.SectionAdapter
import com.alamin.attendanceassistant.viewmodel.SectionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SectionFragment"

@AndroidEntryPoint
class SectionFragment : Fragment() {
    @Inject
    lateinit var sectionAdapter: SectionAdapter

    @Inject
    lateinit var customAlertDialog: CustomAlertDialog

    @Inject
    lateinit var customOptionMenu: CustomOptionMenu

    private lateinit var binding: FragmentSectionBinding
    private lateinit var sectionViewModel: SectionViewModel

    // private lateinit var subjectViewModel: SubjectViewModel
    private val arg by navArgs<SectionFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSectionBinding.inflate(layoutInflater)

        sectionViewModel = ViewModelProvider(this)[SectionViewModel::class.java]

        binding.setOnAddSectionClick {
            arg.classId?.let {
                val action = SectionFragmentDirections.actionSectionFragmentToAddSectionDialog(
                    arg.classId,
                    null
                )
                findNavController().navigate(action)
            }
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = sectionAdapter
        }

        lifecycleScope.launch {
            sectionViewModel.message.collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sectionViewModel.getAllSectionByClass(arg.classId).collectLatest {
                    it?.let {
                        with(sectionAdapter) {
                            setDiffUtils(ArrayList(it))
                            setCallBack(object :
                                ApplicationsCallBack.SetOnAdapterItemClickListener<Section> {
                                override fun onAdapterItemClick(dataClass: Section) {
                                    val action =
                                        SectionFragmentDirections.actionSectionFragmentToSubjectFragment(
                                            dataClass.sectionId
                                        )
                                    findNavController().navigate(action)
                                }

                            })

                            setSectionOptionListener(object :
                                ApplicationsCallBack.SetOnAdapterOptionItemClickListener<Section> {
                                override fun onAdapterOptionItemClick(
                                    dataClass: Section,
                                    view: View
                                ) {
                                    customOptionMenu.showOptionMenu(
                                        view.context,
                                        view,
                                        object : ApplicationsCallBack.SetOnOptionMenuClickListener {
                                            override fun onEdit() {
                                                val action =
                                                    SectionFragmentDirections.actionSectionFragmentToAddSectionDialog(
                                                        0,
                                                        dataClass
                                                    )
                                                findNavController().navigate(action)
                                            }

                                            override fun onDelete() {
                                                customAlertDialog.createDialog(
                                                    "Warning !",
                                                    "Do You Want to Remove Section ?",
                                                    R.color.theme,
                                                    object :
                                                        ApplicationsCallBack.SetOnAlertDialogClickListener {
                                                        override fun onPositive() {
                                                            hasSubject(dataClass)
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

                        }
                    }
                }
            }
        }

        return binding.root
    }

    private fun hasSubject(dataClass: Section) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sectionViewModel.getSubjectBySection(dataClass.sectionId).collectLatest {
                    it?.let {
                        Log.d(TAG, "hasSubject: ${it.size}")
                        if (it.isEmpty()) {
                            sectionViewModel.deleteSection(dataClass.sectionId)
                            this.cancel()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Please, Remove All Subject First",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            this.cancel()
                        }
                    }
                }
            }
        }

    }


}