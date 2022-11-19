package com.alamin.attendanceassistant.model.repository.subject_repository

import com.alamin.attendanceassistant.model.data.Subject
import com.alamin.attendanceassistant.model.repository.CommonRepository
import kotlinx.coroutines.flow.Flow

interface SubjectLocalRepository: CommonRepository<Subject> {
    fun getSubjectBySection(sectionId:Int): Flow<List<Subject>>
}