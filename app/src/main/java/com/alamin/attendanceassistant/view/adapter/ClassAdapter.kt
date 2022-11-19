package com.alamin.attendanceassistant.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alamin.attendanceassistant.databinding.RowClassBinding
import com.alamin.attendanceassistant.model.data.ClassModel
import com.alamin.attendanceassistant.utils.ApplicationsCallBack
import javax.inject.Inject

class ClassAdapter @Inject constructor(private val classDiffUtils: ClassDiffUtils): RecyclerView.Adapter<ClassAdapter.ClassViewHolder>() {

    private var  classList = arrayListOf<ClassModel>()
    private lateinit var setOnAdapterItemClickListener: ApplicationsCallBack.SetOnAdapterItemClickListener<ClassModel>

    inner class ClassViewHolder(private val binding: RowClassBinding): RecyclerView.ViewHolder(binding.root){
            fun binding(classModel: ClassModel){
                binding.classModel = classModel
                binding.setOnAdapterItemClickListener = setOnAdapterItemClickListener
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ClassViewHolder(RowClassBinding.inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        holder.binding(classList[position])
    }

    override fun getItemCount(): Int {
        return classList.size
    }

    fun setDiffUtils(newList: ArrayList<ClassModel>){
        classDiffUtils.setDiffUtils(classList,newList)
        val diffResult = DiffUtil.calculateDiff(classDiffUtils)
        classList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun setCallBack(setOnAdapterItemClickListener: ApplicationsCallBack.SetOnAdapterItemClickListener<ClassModel>){
        this.setOnAdapterItemClickListener = setOnAdapterItemClickListener
    }

}