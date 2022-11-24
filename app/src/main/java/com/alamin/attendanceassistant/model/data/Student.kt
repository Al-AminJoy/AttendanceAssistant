package com.alamin.attendanceassistant.model.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Entity
@Parcelize
data class Student(
    val studentId:Int,
    val studentName:String): Parcelable
