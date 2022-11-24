package com.alamin.attendanceassistant.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StudentHolder(
    val studentList: List<Student>
):Parcelable
