package com.alamin.attendanceassistant.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StudentAttendance(
val studentId:Int,
val studentName:String,
var isPresent: Boolean = false): Parcelable