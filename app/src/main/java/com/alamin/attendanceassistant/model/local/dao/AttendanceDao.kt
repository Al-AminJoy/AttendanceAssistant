package com.alamin.attendanceassistant.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.alamin.attendanceassistant.model.data.Attendance
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Insert
    suspend fun insertAttendance(attendance: Attendance)

    @Update
    suspend fun updateAttendance(attendance: Attendance)

    @Query("DELETE FROM Attendance WHERE attendanceId=:attendanceId")
    suspend fun deleteAttendance(attendanceId:Int)

    @Query("SELECT * FROM Attendance WHERE attendanceId=:attendanceId")
    fun getAttendanceById(attendanceId: Int): Flow<Attendance>

    @Query("SELECT * FROM Attendance WHERE subjectId=:subjectId")
    fun getAttendanceBySubject(subjectId: Int): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance")
    fun getAllAttendance(): Flow<List<Attendance>>
}