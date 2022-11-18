package com.alamin.attendanceassistant.view.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alamin.attendanceassistant.R
import com.alamin.attendanceassistant.databinding.FragmentAddClassDialogBinding
import com.alamin.attendanceassistant.databinding.FragmentHomeBinding
import com.alamin.attendanceassistant.view_model.ClassViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddClassDialog : DialogFragment() {
    lateinit var classViewModel: ClassViewModel
    private lateinit var binding: FragmentAddClassDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddClassDialogBinding.inflate(layoutInflater)
        classViewModel = ViewModelProvider(this)[ClassViewModel::class.java]

        binding.classViewModel = classViewModel
        binding.lifecycleOwner = this

        binding.setOnClassSubmit {
            classViewModel.createClass()
            dismiss()
        }

        lifecycleScope.launch {
            classViewModel.message.collect{
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            // Set Match Parent for Full Screen Dialog
            val width: Int = ViewGroup.LayoutParams.MATCH_PARENT;
            val height: Int = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog!!.window?.setLayout(width, height)
        }
    }


}