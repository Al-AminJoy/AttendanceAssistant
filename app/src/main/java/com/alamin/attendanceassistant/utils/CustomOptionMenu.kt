package com.alamin.attendanceassistant.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import com.alamin.attendanceassistant.R
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class CustomOptionMenu @Inject constructor() {

    fun showOptionMenu(context:Context,view:View,setOnOptionMenuClickListener: ApplicationsCallBack.SetOnOptionMenuClickListener){
        val popupMenu= PopupMenu(context,view)
        with(popupMenu){
            inflate(R.menu.subject_options_menu)
            setOnMenuItemClickListener {item->

                when(item.itemId) {
                    R.id.btnEdit->{
                        setOnOptionMenuClickListener.onEdit()
                    }
                    R.id.btnDelete->{
                        setOnOptionMenuClickListener.onDelete()

                        /*customAlertDialog.createDialog("Warning !",
                            "Do You Want to Remove Subject ?",
                            R.color.theme,
                            object : ApplicationsCallBack.SetOnAlertDialogClickListener{
                                override fun onPositive() {
                                    subjectViewModel.deleteSubject(dataClass.subjectId)
                                    attendanceViewModel.deleteAttendanceBySubject(dataClass.subjectId)
                                }

                                override fun onNegative() {
                                    Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show()
                                }

                            })*/
                    }
                }
                true
            }
            show()
        }
    }

}