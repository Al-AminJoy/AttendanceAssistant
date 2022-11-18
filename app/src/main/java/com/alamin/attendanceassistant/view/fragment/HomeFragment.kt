package com.alamin.attendanceassistant.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alamin.attendanceassistant.R
import com.alamin.attendanceassistant.databinding.FragmentHomeBinding
import com.alamin.attendanceassistant.di.qualifiers.ClassQualifier
import com.alamin.attendanceassistant.model.repository.Repository
import com.alamin.attendanceassistant.view_model.ClassViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var classViewModel: ClassViewModel

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        classViewModel = ViewModelProvider(this)[ClassViewModel::class.java]

        binding.setOnAddClassClick {
            findNavController().navigate(R.id.action_homeFragment_to_addClassDialog)
        }

        return binding.root
    }


}