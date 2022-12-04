package com.alamin.attendanceassistant.model.data

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Entity
@Parcelize
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val subjectId:Int,
    var subjectName:String,
    val subjectColor:String,
    val sectionId:Int,
    var studentHolder: @RawValue StudentHolder):Parcelable
