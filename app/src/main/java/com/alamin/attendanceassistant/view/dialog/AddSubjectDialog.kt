package com.alamin.attendanceassistant.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alamin.attendanceassistant.databinding.FragmentAddSubjectDialogBinding
import com.alamin.attendanceassistant.viewmodel.SubjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddSubjectDialog : DialogFragment() {
    private lateinit var subjectViewModel: SubjectViewModel
    private lateinit var binding: FragmentAddSubjectDialogBinding
    private val arg by navArgs<AddSubjectDialogArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddSubjectDialogBinding.inflate(layoutInflater)

        subjectViewModel = ViewModelProvider(this)[SubjectViewModel::class.java]

        binding.subjectViewModel = subjectViewModel
        binding.lifecycleOwner = this

        if (arg.sectionId == 0){
            arg.subject?.let { subjectViewModel.setSubject(it) }
        }

        binding.setOnSubjectSubmit {
            if (arg.sectionId == 0){
                arg.subject?.let { sub -> subjectViewModel.updateSubject(sub) }
            }else{
                arg.sectionId?.let { sec -> subjectViewModel.insertSubject(sec) }
            }
        }

        lifecycleScope.launch {
            subjectViewModel.message.collect{
                if (it.lowercase() == "Success".lowercase()) {
                    if (arg.sectionId == 0){
                        val action = AddSubjectDialogDirections.actionAddSubjectDialogToSubjectFragment(arg.subject!!.sectionId)
                        findNavController().navigate(action)
                    }
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
            val width: Int = ViewGroup.LayoutParams.MATCH_PARENT;
            val height: Int = ViewGroup.LayoutParams.WRAP_CONTENT;
            it.window?.setLayout(width, height)        }
    }

}