package com.alamin.attendanceassistant.utils

interface ApplicationsCallBack {

    interface SetOnAdapterItemClickListener<T>{
        fun onAdapterItemClick(dataClass: T)
    }


}