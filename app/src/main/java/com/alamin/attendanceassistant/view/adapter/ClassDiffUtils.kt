package com.alamin.attendanceassistant.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.alamin.attendanceassistant.model.data.ClassModel
import javax.inject.Inject

class ClassDiffUtils @Inject constructor(): DiffUtil.Callback() {
    private var oldList = arrayListOf<ClassModel>()
    private var newList = arrayListOf<ClassModel>()

    fun setDiffUtils(oldList:ArrayList<ClassModel>,newList:ArrayList<ClassModel>){
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
        return oldList[oldItemPosition].classId == newList[newItemPosition].classId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}