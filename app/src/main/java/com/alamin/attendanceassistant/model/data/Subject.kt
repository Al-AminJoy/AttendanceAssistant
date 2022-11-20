package com.alamin.attendanceassistant.model.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val subjectId:Int,
    val subjectName:String,
    val subjectColor:String,
    val sectionId:Int):Parcelable
