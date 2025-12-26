package com.alamin.attendanceassistant.view_model

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.attendanceassistant.di.qualifiers.SectionLocalQualifier
import com.alamin.attendanceassistant.di.qualifiers.SubjectLocalQualifier
import com.alamin.attendanceassistant.model.data.Section
import com.alamin.attendanceassistant.model.data.Subject
import com.alamin.attendanceassistant.model.repository.section_repository.SectionLocalRepository
import com.alamin.attendanceassistant.model.repository.subject_repository.SubjectLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "SectionViewModel"

@HiltViewModel
class SectionViewModel @Inject constructor(
    @SectionLocalQualifier private val sectionLocalRepository: SectionLocalRepository,
    @SubjectLocalQualifier private val subjectLocalRepository: SubjectLocalRepository
) : ViewModel() {

    val inputSectionName = MutableStateFlow<String>("")

    val message = MutableSharedFlow<String>()


    fun getAllSectionByClass(classId: Int): StateFlow<List<Section>?> {
        Log.d(TAG, "getAllSectionByClass: ${sectionLocalRepository.getSectionByClass(classId)}")
        return sectionLocalRepository.getSectionByClass(classId).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            null
        )
    }


    fun insertSection(classId: Int) {
        val sectionName = inputSectionName.value
        viewModelScope.launch {
            if (TextUtils.isEmpty(sectionName) || sectionName == null) {
                message.emit("Please, Input Name")
            } else {
                val section = Section(0, sectionName, classId)
                withContext(IO) {
                    sectionLocalRepository.create(section)
                }
                message.emit("Success")
            }

        }
    }

    fun deleteSection(sectionId: Int) {
        viewModelScope.launch {
            withContext(IO) {
                sectionLocalRepository.delete(sectionId)
            }
            message.emit("Success")
        }
    }

    fun setSection(section: Section) {
        inputSectionName.value = section.sectionName
    }

    fun updateSection(section: Section) {
        val sectionName = inputSectionName.value
        viewModelScope.launch {
            if (TextUtils.isEmpty(sectionName) || sectionName == null) {
                message.emit("Please, Input Section name")
            } else {
                section.sectionName = sectionName
                withContext(IO) {
                    sectionLocalRepository.update(section)
                }

            }
            message.emit("Success")
        }
    }

    fun getSubjectBySection(sectionId: Int): StateFlow<List<Subject>?> = subjectLocalRepository
        .getSubjectBySection(sectionId).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            null
        )


}