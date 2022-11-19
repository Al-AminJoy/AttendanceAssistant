package com.alamin.attendanceassistant.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.alamin.attendanceassistant.R
import com.alamin.attendanceassistant.databinding.FragmentHomeBinding
import com.alamin.attendanceassistant.di.qualifiers.ClassQualifier
import com.alamin.attendanceassistant.model.data.ClassModel
import com.alamin.attendanceassistant.model.repository.Repository
import com.alamin.attendanceassistant.utils.ApplicationsClassBack
import com.alamin.attendanceassistant.view.adapter.ClassAdapter
import com.alamin.attendanceassistant.view_model.ClassViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var classAdapter: ClassAdapter

    lateinit var classViewModel: ClassViewModel

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        classViewModel = ViewModelProvider(this)[ClassViewModel::class.java]

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = classAdapter
        }

        lifecycleScope.launchWhenCreated {
            classViewModel.classList.collectLatest {
                it?.let {
                    with(classAdapter){
                        setDiffUtils(ArrayList(it))
                        setCallBack(object : ApplicationsClassBack.SetOnClassClickListener{
                            override fun onClassClick(classModel: ClassModel) {
                                val action = HomeFragmentDirections.actionHomeFragmentToSectionFragment(classModel)
                                findNavController().navigate(action)
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


}