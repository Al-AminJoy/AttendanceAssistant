package com.alamin.attendanceassistant.view_model

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.attendanceassistant.di.qualifiers.SectionLocalQualifier
import com.alamin.attendanceassistant.model.data.Section
import com.alamin.attendanceassistant.model.repository.section_repository.SectionLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SectionViewModel @Inject constructor(@SectionLocalQualifier private val sectionLocalRepository: SectionLocalRepository): ViewModel() {

    val inputSectionName = MutableStateFlow<String>("")

    val message = MutableSharedFlow<String>()

    val getAllSection: StateFlow<List<Section>?> = sectionLocalRepository.getAll().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null
    )

    fun getAllSectionByClass(classId:Int): StateFlow<List<Section>?> {
        return sectionLocalRepository.getSectionByClass(classId).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            null
        )
    }

    fun insertSection(classId: Int){
        val sectionName = inputSectionName.value
        viewModelScope.launch {
            if (TextUtils.isEmpty(sectionName) || sectionName == null){
                message.emit("Please, Input Name")
            }else{
                val section = Section(0,sectionName,classId)
                withContext(IO){
                    sectionLocalRepository.create(section)
                }
                message.emit("Success")
            }

        }
    }

    fun deleteSection(sectionId: Int) {
        viewModelScope.launch {
            withContext(IO){
                sectionLocalRepository.delete(sectionId)
            }
            message.emit("Success")
        }
    }

}