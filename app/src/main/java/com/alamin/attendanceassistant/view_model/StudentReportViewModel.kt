package com.alamin.attendanceassistant.view_model

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.attendanceassistant.di.qualifiers.AttendanceLocalQualifier
import com.alamin.attendanceassistant.di.qualifiers.SubjectLocalQualifier
import com.alamin.attendanceassistant.model.data.Attendance
import com.alamin.attendanceassistant.model.data.Student
import com.alamin.attendanceassistant.model.data.StudentHolder
import com.alamin.attendanceassistant.model.data.Subject
import com.alamin.attendanceassistant.model.repository.attendance_repository.AttendanceLocalRepository
import com.alamin.attendanceassistant.model.repository.subject_repository.SubjectLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StudentReportViewModel @Inject constructor(@SubjectLocalQualifier private val subjectLocalRepository: SubjectLocalRepository,
                                                 @AttendanceLocalQualifier private val attendanceLocalRepository: AttendanceLocalRepository ): ViewModel() {

    val message = MutableSharedFlow<String>()
    val present = MutableSharedFlow<Int>()

    fun calculateAttendance(selectedStudent: Int, studentAttendanceList: ArrayList<Attendance>) {

        var presentCount = 0

        for (attendance in studentAttendanceList){

            for (studentAttendance in attendance.studentAttendanceHolder.studentAttendanceList){
                if (studentAttendance.studentId == selectedStudent && studentAttendance.isPresent){
                    presentCount++
                }
            }
        }

        viewModelScope.launch {
            present.emit(presentCount)
        }

    }

    fun getAttendanceBySubject(subjectId:Int): Flow<List<Attendance>?> =
        attendanceLocalRepository.getAttendanceBySubject(subjectId).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            null
        )


    fun getSubjectById(subjectId:Int): StateFlow<Subject?> = subjectLocalRepository.getById(subjectId).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null
    )

}