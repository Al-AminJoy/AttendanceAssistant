package com.alamin.attendanceassistant.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.alamin.attendanceassistant.model.data.Attendance
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertAttendance(attendance: Attendance)

    @Update
    suspend fun updateAttendance(attendance: Attendance)

    @Query("DELETE FROM Attendance WHERE attendanceId=:attendanceId")
    suspend fun deleteAttendance(attendanceId:String)

    @Query("SELECT * FROM Attendance WHERE attendanceId=:attendanceId")
    fun getAttendanceById(attendanceId: String): Flow<Attendance>

    @Query("SELECT * FROM Attendance WHERE attendanceId=:attendanceId AND subjectId=:subjectId")
    fun getAttendanceByDateAndSubject(subjectId: Int,attendanceId: String): Flow<Attendance>

    @Query("SELECT * FROM Attendance WHERE subjectId=:subjectId")
    fun getAttendanceBySubject(subjectId: Int): Flow<List<Attendance>>

    @Query("SELECT * FROM Attendance")
    fun getAllAttendance(): Flow<List<Attendance>>


}