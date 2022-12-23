package com.alamin.attendanceassistant.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.alamin.attendanceassistant.model.data.Section
import javax.inject.Inject

class SectionDiffUtils @Inject constructor(): DiffUtil.Callback() {

    private var oldList = arrayListOf<Section>()
    private var newList = arrayListOf<Section>()

    fun setDiffUtils(oldList:ArrayList<Section>, newList:ArrayList<Section>){
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

        return oldList[oldItemPosition].sectionId == newList[newItemPosition].sectionId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        //return oldList[oldItemPosition] == newList[newItemPosition]
        return when{
            oldList[oldItemPosition].sectionId != newList[newItemPosition].sectionId -> false
            oldList[oldItemPosition].classId != newList[newItemPosition].classId -> false
            oldList[oldItemPosition].sectionName != newList[newItemPosition].sectionName -> false
            else -> true
        }
    }
}