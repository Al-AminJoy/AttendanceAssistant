package com.alamin.attendanceassistant.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.RawValue

@Entity
data class Attendance(
    @PrimaryKey
    val attendanceId: String,
    val studentAttendanceHolder: @RawValue StudentAttendanceHolder,
    val subjectId:Int)
