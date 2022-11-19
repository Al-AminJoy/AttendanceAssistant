package com.alamin.attendanceassistant.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.alamin.attendanceassistant.databinding.FragmentSectionBinding
import com.alamin.attendanceassistant.model.data.Section
import com.alamin.attendanceassistant.utils.ApplicationsCallBack
import com.alamin.attendanceassistant.view.adapter.SectionAdapter
import com.alamin.attendanceassistant.view_model.SectionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class SectionFragment : Fragment() {
    @Inject
    lateinit var sectionAdapter: SectionAdapter

    private lateinit var binding: FragmentSectionBinding
    private lateinit var sectionViewModel: SectionViewModel
    private val arg by navArgs<SectionFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSectionBinding.inflate(layoutInflater)

        sectionViewModel = ViewModelProvider(this)[SectionViewModel::class.java]

        binding.setOnAddSectionClick {
            val action = SectionFragmentDirections.actionSectionFragmentToAddSectionDialog(arg.classModel)
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = sectionAdapter
        }

        lifecycleScope.launchWhenCreated {
            sectionViewModel.getAllSectionByClass(arg.classModel.classId).collectLatest {
                it?.let {
                    with(sectionAdapter){
                        setDiffUtils(ArrayList(it))
                        setCallBack(object: ApplicationsCallBack.SetOnAdapterItemClickListener<Section>{
                            override fun onAdapterItemClick(dataClass: Section) {
                                val action = SectionFragmentDirections.actionSectionFragmentToSubjectFragment(dataClass)
                                findNavController().navigate(action)
                            }

                        })
                    }
                }
            }
        }

        return binding.root
    }

}