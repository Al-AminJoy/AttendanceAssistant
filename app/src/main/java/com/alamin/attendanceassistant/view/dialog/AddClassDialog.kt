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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
    private val arg by navArgs<AddClassDialogArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddClassDialogBinding.inflate(layoutInflater)
        classViewModel = ViewModelProvider(this)[ClassViewModel::class.java]

        binding.classViewModel = classViewModel
        binding.lifecycleOwner = this

        if (arg.classModel != null){
            arg.classModel?.let { it -> classViewModel.setClass(it)  }
        }

        binding.setOnClassSubmit {
            if (arg.classModel == null){
                classViewModel.createClass()
            }else{
                arg.classModel?.let { it -> classViewModel.updateClass(it)  }

            }
        }

        lifecycleScope.launch {
            classViewModel.message.collect{
                if (it.lowercase() == "Success".lowercase())  {
                    findNavController().navigate(R.id.action_addClassDialog_to_homeFragment)
                    dismiss()
                }
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
            it.window?.setLayout(width, height)
        }
    }


}