package com.alamin.attendanceassistant.utils

import com.alamin.attendanceassistant.model.data.ClassModel

interface ApplicationsClassBack {
    interface SetOnClassClickListener{
        fun onClassClick(classModel: ClassModel)
    }
}