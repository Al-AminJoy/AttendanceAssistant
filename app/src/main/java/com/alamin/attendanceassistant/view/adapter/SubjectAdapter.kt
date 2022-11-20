package com.alamin.attendanceassistant.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alamin.attendanceassistant.databinding.RowSubjectBinding
import com.alamin.attendanceassistant.model.data.Subject
import com.alamin.attendanceassistant.utils.ApplicationsCallBack
import javax.inject.Inject

class SubjectAdapter @Inject constructor(private val subjectDiffUtils: SubjectDiffUtils): RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    private var subjectList = arrayListOf<Subject>()
    private lateinit var setOnAdapterItemClickListener: ApplicationsCallBack.SetOnAdapterItemClickListener<Subject>

    inner class SubjectViewHolder(private val binding: RowSubjectBinding): RecyclerView.ViewHolder(binding.root){
        fun binding(subject: Subject){
            binding.subject = subject
            binding.setOnSubjectClickListener = setOnAdapterItemClickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SubjectViewHolder(RowSubjectBinding.inflate(inflater,parent,false))
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.binding(subjectList[position])
    }

    override fun getItemCount(): Int {
        return subjectList.size
    }

    fun setDiffUtils(newList: ArrayList<Subject>){
        subjectDiffUtils.setDiffUtils(subjectList,newList)
        val result  = DiffUtil.calculateDiff(subjectDiffUtils)
        subjectList = newList
        result.dispatchUpdatesTo(this)
    }

    fun setSubjectListener(setOnAdapterItemClickListener: ApplicationsCallBack.SetOnAdapterItemClickListener<Subject>){
        this.setOnAdapterItemClickListener = setOnAdapterItemClickListener
    }

}