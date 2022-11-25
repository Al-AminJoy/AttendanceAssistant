package com.alamin.attendanceassistant.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StudentAttendanceHolder (
    val studentAttendanceList : List<StudentAttendance>
    ):Parcelable