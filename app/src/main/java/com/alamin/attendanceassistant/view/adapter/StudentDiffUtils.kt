package com.alamin.attendanceassistant.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.alamin.attendanceassistant.model.data.Student
import javax.inject.Inject

class StudentDiffUtils @Inject constructor(): DiffUtil.Callback() {
    private var oldList = arrayListOf<Student>()
    private var newList = arrayListOf<Student>()

    fun setDiffUtils(oldList:ArrayList<Student>, newList:ArrayList<Student>){
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