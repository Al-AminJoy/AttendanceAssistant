package com.alamin.attendanceassistant.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.RawValue

@Entity
data class Attendance(
    @PrimaryKey(autoGenerate = true)
    val attendanceId: Int,
    val attendanceDate: Long,
    val studentAttendanceHolder: @RawValue StudentAttendanceHolder,
    val subjectId:Int)
