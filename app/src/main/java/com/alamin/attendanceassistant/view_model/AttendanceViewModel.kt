package com.alamin.attendanceassistant.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.attendanceassistant.di.qualifiers.AttendanceLocalQualifier
import com.alamin.attendanceassistant.di.qualifiers.SubjectLocalQualifier
import com.alamin.attendanceassistant.model.data.Attendance
import com.alamin.attendanceassistant.model.data.StudentAttendance
import com.alamin.attendanceassistant.model.data.StudentAttendanceHolder
import com.alamin.attendanceassistant.model.data.Subject
import com.alamin.attendanceassistant.model.repository.attendance_repository.AttendanceLocalRepository
import com.alamin.attendanceassistant.model.repository.subject_repository.SubjectLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "AttendanceViewModel"

@HiltViewModel
class AttendanceViewModel @Inject constructor(@AttendanceLocalQualifier private val attendanceLocalRepository: AttendanceLocalRepository): ViewModel() {

    val message = MutableSharedFlow<String>()

    var studentAttendanceFlowList: MutableStateFlow<List<StudentAttendance>?> = MutableStateFlow(null)

    fun getAttendanceById(attendanceId: String): StateFlow<Attendance?> =
        attendanceLocalRepository.getAttendanceByAttendanceId(attendanceId).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            null)


    fun getStudentListOfAttendance(studentList: ArrayList<StudentAttendance>,subject: Subject) {

        val studentAttendanceList  = arrayListOf<StudentAttendance>()


        if (studentList.isEmpty()) {
            if (subject.studentHolder.studentList.isNotEmpty()) {
                for (student in subject.studentHolder.studentList) {
                    val studentAttendance =
                        StudentAttendance(student.studentId, student.studentName)
                    studentAttendanceList.add(studentAttendance)
                }
            }
        } else {
            studentAttendanceList.addAll(studentList)

            for (student in subject.studentHolder.studentList){
                var wasPrevious = false
                for (studentAttendance in studentList ) {
                    if (student.studentId == studentAttendance.studentId){
                        wasPrevious = true
                        break
                    }
                }
                if (!wasPrevious){
                    val studentAttendance =
                        StudentAttendance(student.studentId, student.studentName)
                    studentAttendanceList.add(studentAttendance)
                }
            }

        }
        viewModelScope.launch {
            studentAttendanceFlowList.emit(studentAttendanceList)
        }

    }
    fun createAttendance(attendanceId: String, studentAttendanceList: ArrayList<StudentAttendance>, subjectId: Int) {
        viewModelScope.launch(IO) {
            val studentAttendanceHolder = StudentAttendanceHolder(studentAttendanceList)
            val attendance = Attendance(attendanceId,studentAttendanceHolder,subjectId)
            attendanceLocalRepository.create(attendance)
            message.emit("Success")
        }
    }

}