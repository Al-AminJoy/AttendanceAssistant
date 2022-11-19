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
import androidx.navigation.fragment.navArgs
import com.alamin.attendanceassistant.R
import com.alamin.attendanceassistant.databinding.FragmentAddSectionDialogBinding
import com.alamin.attendanceassistant.view_model.SectionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
@AndroidEntryPoint
class AddSectionDialog : DialogFragment() {

    private lateinit var sectionViewModel: SectionViewModel
    private lateinit var binding: FragmentAddSectionDialogBinding
    private val arg by navArgs<AddSectionDialogArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddSectionDialogBinding.inflate(layoutInflater)

        sectionViewModel = ViewModelProvider(this)[SectionViewModel::class.java]

        binding.sectionViewModel = sectionViewModel
        binding.lifecycleOwner = this

        binding.setOnSectionSubmit {
            sectionViewModel.insertSection(arg.classModel.classId)
        }

        lifecycleScope.launch {
            sectionViewModel.message.collect{
                if (it.lowercase() == "Success".lowercase()) dismiss()
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val width: Int = ViewGroup.LayoutParams.MATCH_PARENT;
            val height: Int = ViewGroup.LayoutParams.WRAP_CONTENT;
            it.window?.setLayout(width, height)
        }
    }


}