package com.alamin.attendanceassistant.model.data

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromStudentHolder(studentHolder: StudentHolder): String{
        return Gson().toJson(studentHolder)
    }

    @TypeConverter
    fun toStudentHolder(studentHolder: String): StudentHolder{
        return Gson().fromJson(studentHolder,StudentHolder::class.java)
    }

}