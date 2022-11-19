package com.alamin.attendanceassistant.view_model

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.attendanceassistant.di.qualifiers.SubjectLocalQualifier
import com.alamin.attendanceassistant.model.data.Subject
import com.alamin.attendanceassistant.model.repository.subject_repository.SubjectLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(@SubjectLocalQualifier private val subjectLocalRepository: SubjectLocalRepository ): ViewModel() {

    val inputSubjectName = MutableStateFlow<String>("")
    val message = MutableSharedFlow<String>()

    fun getSubjectBySection(sectionId:Int): StateFlow<List<Subject>?> = subjectLocalRepository.getSubjectBySection(sectionId).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null
    )

    fun insertSubject(sectionId:Int){
        val subjectName = inputSubjectName.value

        viewModelScope.launch {
            if (TextUtils.isEmpty(subjectName) || subjectName == null){
                message.emit("Please, Input Subject name")
            }else{
                withContext(IO){
                    val subject = Subject(0,subjectName,"FFFFFF",sectionId)
                    subjectLocalRepository.create(subject)
                }
                message.emit("Success")
            }
        }
    }

}