package com.alamin.attendanceassistant.utils

interface ApplicationsCallBack {

    interface SetOnAdapterItemClickListener<T>{
        fun onAdapterItemClick(dataClass: T)
    }

    interface SetOnAttendanceClickListener<T>{
        fun onAdapterItemClick(dataClass: T,isPresent: Boolean)
    }

    interface SetOnStudentClickListener<T>{
        fun onAdapterItemClick(dataClass: T,isUpdate: Boolean)

    }

}