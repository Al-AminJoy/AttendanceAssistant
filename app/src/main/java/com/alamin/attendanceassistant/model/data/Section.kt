package com.alamin.attendanceassistant.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Section(
    @PrimaryKey(autoGenerate = true)
    val sectionId:Int,
    val sectionName:String,
    val classId:Int)
