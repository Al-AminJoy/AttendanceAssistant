package com.alamin.attendanceassistant.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alamin.attendanceassistant.databinding.RowSectionBinding
import com.alamin.attendanceassistant.model.data.ClassModel
import com.alamin.attendanceassistant.model.data.Section
import com.alamin.attendanceassistant.model.data.Subject
import com.alamin.attendanceassistant.utils.ApplicationsCallBack
import javax.inject.Inject

class SectionAdapter @Inject constructor(private val sectionDiffUtils: SectionDiffUtils): RecyclerView.Adapter<SectionAdapter.SectionViewHolder>() {

    private var sectionList = arrayListOf<Section>()
    private lateinit var setOnAdapterItemClickListener: ApplicationsCallBack.SetOnAdapterItemClickListener<Section>
    private lateinit var setOnAdapterOptionItemClickListener: ApplicationsCallBack.SetOnAdapterOptionItemClickListener<Section>


    inner class SectionViewHolder(val binding: RowSectionBinding): RecyclerView.ViewHolder(binding.root){
        fun binding(section:Section){
            binding.section = section
            binding.setOnSectionClickListener = setOnAdapterItemClickListener
            binding.setOnOptionClickListener {
                setOnAdapterOptionItemClickListener.onAdapterOptionItemClick(section,it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val  layoutInflater = LayoutInflater.from(parent.context)
        return SectionViewHolder(RowSectionBinding.inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        holder.binding(sectionList[position])
    }

    override fun getItemCount(): Int {
        return sectionList.size
    }

    fun setDiffUtils(newList: ArrayList<Section>){
        sectionDiffUtils.setDiffUtils(sectionList,newList)
        val diffResult = DiffUtil.calculateDiff(sectionDiffUtils)
        sectionList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun setCallBack(setOnAdapterItemClickListener: ApplicationsCallBack.SetOnAdapterItemClickListener<Section>){
        this.setOnAdapterItemClickListener = setOnAdapterItemClickListener
    }

    fun setSectionOptionListener(setOnAdapterOptionItemClickListener: ApplicationsCallBack.SetOnAdapterOptionItemClickListener<Section>){
        this.setOnAdapterOptionItemClickListener = setOnAdapterOptionItemClickListener
    }
}