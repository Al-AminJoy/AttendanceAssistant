package com.alamin.attendanceassistant.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.alamin.attendanceassistant.model.data.StudentAttendance
import javax.inject.Inject

class AttendanceDiffUtils @Inject constructor(): DiffUtil.Callback() {
    private var oldList = arrayListOf<StudentAttendance>()
    private var newList = arrayListOf<StudentAttendance>()

    fun setDiffUtils(oldList:ArrayList<StudentAttendance>, newList:ArrayList<StudentAttendance>){
        this.oldList = oldList
        this.newList = newList
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].studentId == newList[newItemPosition].studentId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}