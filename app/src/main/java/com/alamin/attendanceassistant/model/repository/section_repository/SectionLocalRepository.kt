package com.alamin.attendanceassistant.model.repository.section_repository

import com.alamin.attendanceassistant.model.data.Section
import com.alamin.attendanceassistant.model.repository.CommonRepository
import kotlinx.coroutines.flow.Flow

interface SectionLocalRepository: CommonRepository<Section> {
    fun getSectionByClass(classId:Int): Flow<List<Section>>
}