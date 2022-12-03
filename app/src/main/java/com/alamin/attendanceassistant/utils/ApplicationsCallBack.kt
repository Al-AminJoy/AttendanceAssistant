package com.alamin.attendanceassistant.utils

import android.view.View

interface ApplicationsCallBack {

    interface SetOnAdapterItemClickListener<T>{
        fun onAdapterItemClick(dataClass: T)
    }

    interface SetOnAttendanceClickListener<T>{
        fun onAdapterItemClick(dataClass: T,isPresent: Boolean)
    }

    interface SetOnAdapterOptionItemClickListener<T>{
        fun onAdapterOptionItemClick(dataClass: T,view:View)
    }

    interface SetOnStudentClickListener<T>{
        fun onAdapterItemClick(dataClass: T,isUpdate: Boolean)

    }

    interface SetOnAlertDialogClickListener{
        fun onPositive()
        fun onNegative()
    }

    interface SetOnOptionMenuClickListener{
        fun onEdit()
        fun onDelete()
    }

}