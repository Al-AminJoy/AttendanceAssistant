package com.alamin.attendanceassistant.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ClassModel(
    @PrimaryKey(autoGenerate = true)
    val classId:Int,
    val className:String)
