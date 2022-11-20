package com.alamin.attendanceassistant.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.alamin.attendanceassistant.model.data.Section
import com.alamin.attendanceassistant.model.data.Subject
import javax.inject.Inject

class SubjectDiffUtils @Inject constructor(): DiffUtil.Callback() {
    private var oldList = arrayListOf<Subject>()
    private var newList = arrayListOf<Subject>()

    fun setDiffUtils(oldList:ArrayList<Subject>, newList:ArrayList<Subject>){
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
        return oldList[oldItemPosition].subjectId == newList[newItemPosition].subjectId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}