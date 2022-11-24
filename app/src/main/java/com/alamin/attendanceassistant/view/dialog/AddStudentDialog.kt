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
import com.alamin.attendanceassistant.databinding.FragmentAddStudentDialogBinding
import com.alamin.attendanceassistant.view_model.SubjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddStudentDialog : DialogFragment() {

    private lateinit var binding: FragmentAddStudentDialogBinding
    private lateinit var subjectViewModel: SubjectViewModel
    private val arg by navArgs<AddStudentDialogArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding  = FragmentAddStudentDialogBinding.inflate(layoutInflater)

        subjectViewModel = ViewModelProvider(this)[SubjectViewModel::class.java]

        binding.subjectViewModel = subjectViewModel
        binding.lifecycleOwner = this

        binding.setOnStudentSubmit {
            subjectViewModel.insertStudentBySubject(arg.subject)
        }

        lifecycleScope.launch {
            subjectViewModel.message.collect{
                if (it.lowercase() == "Success".lowercase()) dismiss()
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val width: Int = ViewGroup.LayoutParams.MATCH_PARENT
            val height: Int = ViewGroup.LayoutParams.WRAP_CONTENT
            it.window?.setLayout(width,height);
        }
    }

}