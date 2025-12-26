package com.alamin.attendanceassistant.viewmodel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.attendanceassistant.di.qualifiers.ClassLocalQualifier
import com.alamin.attendanceassistant.di.qualifiers.SectionLocalQualifier
import com.alamin.attendanceassistant.model.data.ClassModel
import com.alamin.attendanceassistant.model.data.Section
import com.alamin.attendanceassistant.model.repository.class_repository.ClassLocalRepository
import com.alamin.attendanceassistant.model.repository.section_repository.SectionLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "ClassViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(@ClassLocalQualifier private val repository: ClassLocalRepository,
                                        @SectionLocalQualifier private val sectionLocalRepository: SectionLocalRepository): ViewModel() {

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

    fun getAllSectionByClass(classId:Int): StateFlow<List<Section>?> {
        Log.d(TAG, "getAllSectionByClass: ${sectionLocalRepository.getSectionByClass(classId)}")
        return sectionLocalRepository.getSectionByClass(classId).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            null
        )
    }

    fun deleteClass(classId: Int) {
            viewModelScope.launch {
                withContext(IO){
                    repository.delete(classId)
                }
                message.emit("Success")
            }

    }


    fun setClass(classModel: ClassModel) {
        inputClassName.value = classModel.className
    }

    fun updateClass(classModel: ClassModel){
        val className = inputClassName.value
        viewModelScope.launch {
            if (TextUtils.isEmpty(className) || className == null){
                message.emit("Please, Input Class name")
            }else{
                classModel.className = className
                withContext(IO){
                repository.update(classModel)
            }
        }
            message.emit("Success")
        }
    }

}