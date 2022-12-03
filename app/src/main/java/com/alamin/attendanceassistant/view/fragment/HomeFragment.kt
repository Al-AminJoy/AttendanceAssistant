package com.alamin.attendanceassistant.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.alamin.attendanceassistant.R
import com.alamin.attendanceassistant.databinding.FragmentHomeBinding
import com.alamin.attendanceassistant.model.data.ClassModel
import com.alamin.attendanceassistant.model.data.Section
import com.alamin.attendanceassistant.utils.ApplicationsCallBack
import com.alamin.attendanceassistant.utils.CustomAlertDialog
import com.alamin.attendanceassistant.utils.CustomOptionMenu
import com.alamin.attendanceassistant.view.adapter.ClassAdapter
import com.alamin.attendanceassistant.view_model.ClassViewModel
import com.alamin.attendanceassistant.view_model.SectionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

private const val TAG = "HomeFragment"
@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var classAdapter: ClassAdapter
    @Inject
    lateinit var customAlertDialog: CustomAlertDialog
    @Inject
    lateinit var customOptionMenu : CustomOptionMenu

    private lateinit var classViewModel: ClassViewModel
    private lateinit var sectionViewModel: SectionViewModel

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        classViewModel = ViewModelProvider(this)[ClassViewModel::class.java]
        sectionViewModel = ViewModelProvider(this)[SectionViewModel::class.java]

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = classAdapter
        }

        lifecycleScope.launchWhenCreated {
            classViewModel.classList.collectLatest {
                it?.let {
                    with(classAdapter){
                        setDiffUtils(ArrayList(it))
                        setCallBack(object : ApplicationsCallBack.SetOnAdapterItemClickListener<ClassModel> {
                            override fun onAdapterItemClick(classModel: ClassModel) {
                                val action = HomeFragmentDirections.actionHomeFragmentToSectionFragment(classModel)
                                findNavController().navigate(action)
                            }

                        })

                        setClassOptionListener(object : ApplicationsCallBack.SetOnAdapterOptionItemClickListener<ClassModel>{
                            override fun onAdapterOptionItemClick(dataClass: ClassModel, view: View) {
                                customOptionMenu.showOptionMenu(view.context,view,object : ApplicationsCallBack.SetOnOptionMenuClickListener{
                                    override fun onEdit() {

                                    }

                                    override fun onDelete() {
                                        customAlertDialog.createDialog("Warning !",
                                            "Do You Want to Remove Class ?",
                                            R.color.theme,
                                            object : ApplicationsCallBack.SetOnAlertDialogClickListener{
                                                override fun onPositive() {
                                                    hasSection(dataClass)
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

        binding.setOnAddClassClick {
            findNavController().navigate(R.id.action_homeFragment_to_addClassDialog)
        }

        return binding.root
    }

    private fun hasSection(dataClass: ClassModel) {
        lifecycleScope.launchWhenCreated {
            sectionViewModel.getAllSectionByClass(dataClass.classId).collectLatest {
                it?.let {
                    Log.d(TAG, "hasSubject: ${it.size}")
                    if (it.isEmpty()){
                        classViewModel.deleteClass(dataClass.classId)
                    }else{
                        Toast.makeText(
                            requireContext(),
                            "Please, Remove All Section First",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }

        }

    }


}