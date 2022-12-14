package com.alamin.attendanceassistant.model.repository.attendance_repository

import com.alamin.attendanceassistant.model.data.Attendance
import com.alamin.attendanceassistant.model.repository.CommonRepository
import kotlinx.coroutines.flow.Flow

interface AttendanceLocalRepository: CommonRepository<Attendance> {
    fun getAttendanceBySubject(subjectId:Int): Flow<List<Attendance>>
    fun getAttendanceByAttendanceId(attendanceId:String): Flow<Attendance>
    suspend fun deleteAttendanceId(attendanceId:String)
    suspend fun deleteAttendanceBuSubjectId(subjectId:Int)
}