package com.alamin.attendanceassistant.view_model

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.attendanceassistant.di.qualifiers.SubjectLocalQualifier
import com.alamin.attendanceassistant.model.data.Student
import com.alamin.attendanceassistant.model.data.StudentHolder
import com.alamin.attendanceassistant.model.data.Subject
import com.alamin.attendanceassistant.model.repository.subject_repository.SubjectLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AttendanceHolderViewModel @Inject constructor(@SubjectLocalQualifier private val subjectLocalRepository: SubjectLocalRepository ): ViewModel() {

    val message = MutableSharedFlow<String>()

    fun getSubjectById(subjectId:Int): StateFlow<Subject?> = subjectLocalRepository.getById(subjectId).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null
    )


}