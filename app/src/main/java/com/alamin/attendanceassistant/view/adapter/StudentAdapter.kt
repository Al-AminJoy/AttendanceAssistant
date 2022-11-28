package com.alamin.attendanceassistant.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alamin.attendanceassistant.databinding.RowStudentBinding
import com.alamin.attendanceassistant.model.data.Student
import com.alamin.attendanceassistant.utils.ApplicationsCallBack
import javax.inject.Inject

class StudentAdapter @Inject constructor(private val studentDiffUtils: StudentDiffUtils): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var studentList = arrayListOf<Student>()
    private lateinit var adapterItemClickListener: ApplicationsCallBack.SetOnStudentClickListener<Student>

    inner class StudentViewHolder(val binding: RowStudentBinding): RecyclerView.ViewHolder(binding.root){
        fun binding(student: Student){
            binding.student = student
            binding.setOnStudentClickListener = adapterItemClickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StudentViewHolder(RowStudentBinding.inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.binding(studentList[position])
    }

    override fun getItemCount(): Int {
       return studentList.size
    }

    fun setStudentDiffUtils(newList: ArrayList<Student>){
        studentDiffUtils.setDiffUtils(studentList,newList)
        val diffResult = DiffUtil.calculateDiff(studentDiffUtils)
        studentList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun setAdapterClickListener(adapterItemClickListener: ApplicationsCallBack.SetOnStudentClickListener<Student>){
        this.adapterItemClickListener = adapterItemClickListener
    }

}