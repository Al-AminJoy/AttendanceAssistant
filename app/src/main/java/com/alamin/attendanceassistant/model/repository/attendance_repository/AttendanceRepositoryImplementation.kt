package com.alamin.attendanceassistant.model.repository.attendance_repository

import com.alamin.attendanceassistant.model.data.Attendance
import com.alamin.attendanceassistant.model.local.LocalDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AttendanceRepositoryImplementation @Inject constructor(private val localDatabase: LocalDatabase): AttendanceLocalRepository {

    private val attendanceDao = localDatabase.attendanceDao()

    override fun getAttendanceBySubject(subjectId: Int): Flow<List<Attendance>> {
        return attendanceDao.getAttendanceBySubject(subjectId)
    }

    override fun getAttendanceByAttendanceId(attendanceId: String): Flow<Attendance> {
        return attendanceDao.getAttendanceById(attendanceId)
    }

    override suspend fun deleteAttendanceId(attendanceId: String) {
        attendanceDao.deleteAttendance(attendanceId)
    }


    override fun getAll(): Flow<List<Attendance>> {
        return attendanceDao.getAllAttendance()
    }

    override fun getById(id: Int): Flow<Attendance> {
        return flow {  }
    }

    override suspend fun create(dataClass: Attendance) {
        attendanceDao.insertAttendance(dataClass)
    }

    override suspend fun update(dataClass: Attendance) {
        attendanceDao.updateAttendance(dataClass)
    }

    override suspend fun delete(id: Int) {

    }
}