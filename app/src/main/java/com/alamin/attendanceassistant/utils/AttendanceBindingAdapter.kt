package com.alamin.attendanceassistant.utils

import android.widget.RadioButton
import androidx.databinding.BindingAdapter

@BindingAdapter("setPresent")
fun RadioButton.setPresent(isPresent: Boolean){
    if (isPresent){
        this.isChecked = isPresent
    }
}

@BindingAdapter("setAbsent")
fun RadioButton.setAbsent(isPresent: Boolean){
    if (!isPresent){
        this.isChecked = isPresent
    }
}