package com.alamin.attendanceassistant.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.attendanceassistant.di.qualifiers.AttendanceLocalQualifier
import com.alamin.attendanceassistant.model.data.Attendance
import com.alamin.attendanceassistant.model.repository.attendance_repository.AttendanceLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(@AttendanceLocalQualifier private val attendanceLocalRepository: AttendanceLocalRepository): ViewModel() {

    val message = MutableSharedFlow<String>()

  //  fun getAttendanceBySubject

    fun getAttendanceBySubjectAndDate(subjectId: Int, attendanceDate: Long): StateFlow<Attendance?> =
        attendanceLocalRepository.getAttendanceByDateAndSubject(subjectId,attendanceDate).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            null)


}