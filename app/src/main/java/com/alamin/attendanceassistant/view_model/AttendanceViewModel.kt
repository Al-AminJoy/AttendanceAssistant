package com.alamin.attendanceassistant.view_model

import androidx.lifecycle.ViewModel
import com.alamin.attendanceassistant.di.qualifiers.AttendanceLocalQualifier
import com.alamin.attendanceassistant.model.repository.attendance_repository.AttendanceLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(@AttendanceLocalQualifier private val attendanceLocalRepository: AttendanceLocalRepository): ViewModel() {



}