package com.alamin.attendanceassistant.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alamin.attendanceassistant.databinding.RowAttendanceBinding
import com.alamin.attendanceassistant.model.data.StudentAttendance
import com.alamin.attendanceassistant.utils.ApplicationsCallBack
import javax.inject.Inject

class AttendanceAdapter @Inject constructor(private val attendanceDiffUtils: AttendanceDiffUtils): RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder>() {

    private var attendanceList = arrayListOf<StudentAttendance>()
    private lateinit var adapterItemClickListener: ApplicationsCallBack.SetOnAttendanceClickListener<StudentAttendance>

    inner class AttendanceViewHolder (val binding: RowAttendanceBinding): RecyclerView.ViewHolder(binding.root){
        fun binding(studentAttendance: StudentAttendance){
            binding.student = studentAttendance
            binding.setAttendance = adapterItemClickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AttendanceViewHolder(RowAttendanceBinding.inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
        holder.binding(attendanceList[position])
    }

    override fun getItemCount(): Int {
        return attendanceList.size
    }

    fun setDiffUtils(newList: ArrayList<StudentAttendance>){
        attendanceDiffUtils.setDiffUtils(attendanceList,newList)
        val diffResult = DiffUtil.calculateDiff(attendanceDiffUtils)
        attendanceList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun setAdapterItemClickListener(adapterItemClickListener: ApplicationsCallBack.SetOnAttendanceClickListener<StudentAttendance>){
        this.adapterItemClickListener = adapterItemClickListener
    }

}