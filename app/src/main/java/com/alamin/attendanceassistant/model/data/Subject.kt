package com.alamin.attendanceassistant.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val subjectId:Int,
    val subjectName:String,
    val subjectColor:String,
    val sectionId:Int)
