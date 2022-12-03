package com.alamin.attendanceassistant.view_model

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.attendanceassistant.di.qualifiers.ClassLocalQualifier
import com.alamin.attendanceassistant.model.data.ClassModel
import com.alamin.attendanceassistant.model.repository.class_repository.ClassLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "ClassViewModel"

@HiltViewModel
class ClassViewModel @Inject constructor(@ClassLocalQualifier private val repository: ClassLocalRepository): ViewModel() {

    val message = MutableSharedFlow<String>()
    val inputClassName = MutableStateFlow<String>("")

    val classList: StateFlow<List<ClassModel>?> = repository.getAll().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null
    )

    fun createClass(){
        val name: String = inputClassName.value

        viewModelScope.launch {
        if (TextUtils.isEmpty(name) || name == null){
            message.emit("Please, Input Class")
        }else{
            val classModel = ClassModel(0,name)
            message.emit("Success")
            withContext(IO){
                repository.create(classModel)
            }
        }

        }
    }

    fun deleteClass(classId: Int) {
            viewModelScope.launch {
                withContext(IO){
                    repository.delete(classId)
                }
                message.emit("Success")
            }

    }

}