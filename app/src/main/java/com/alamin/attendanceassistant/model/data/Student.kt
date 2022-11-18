package com.alamin.attendanceassistant.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    @PrimaryKey(autoGenerate = true)
    val studentId:Int,
    val studentName:String,
    val subjectId:Int )
