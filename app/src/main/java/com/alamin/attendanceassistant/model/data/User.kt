package com.alamin.attendanceassistant.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey (autoGenerate = true)
     val userId: Int)
