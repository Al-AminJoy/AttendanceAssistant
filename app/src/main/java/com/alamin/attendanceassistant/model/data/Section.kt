package com.alamin.attendanceassistant.model.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Section(
    @PrimaryKey(autoGenerate = true)
    val sectionId:Int,
    val sectionName:String,
    val classId:Int):Parcelable
