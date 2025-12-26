package com.alamin.attendanceassistant.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.attendanceassistant.di.qualifiers.StudentLocalQualifier
import com.alamin.attendanceassistant.di.qualifiers.SubjectLocalQualifier
import com.alamin.attendanceassistant.model.data.Student
import com.alamin.attendanceassistant.model.data.StudentHolder
import com.alamin.attendanceassistant.model.data.Subject
import com.alamin.attendanceassistant.model.repository.student_repository.StudentLocalRepository
import com.alamin.attendanceassistant.model.repository.subject_repository.SubjectLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class StudentViewModel @Inject constructor(@SubjectLocalQualifier private val subjectLocalRepository: SubjectLocalRepository): ViewModel() {

    val message = MutableSharedFlow<String>()

    fun getSubjectById(subjectId:Int): StateFlow<Subject?> = subjectLocalRepository.getById(subjectId).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null
    )

    fun removeStudent(dataClass: Student, subject: Subject) {

        viewModelScope.launch  {
            withContext(IO){
                val studentList = (ArrayList(subject.studentHolder.studentList))
                studentList.remove(dataClass)
                val studentHolder = StudentHolder(studentList)
                subject.studentHolder = studentHolder
                subjectLocalRepository.update(subject)
            }
            message.emit("Success")

        }
    }

}