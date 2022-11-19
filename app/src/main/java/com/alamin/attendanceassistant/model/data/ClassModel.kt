package com.alamin.attendanceassistant.model.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Parcelize
@Entity
data class ClassModel(
    @PrimaryKey(autoGenerate = true)
    val classId:Int,
    val className:String):Parcelable
