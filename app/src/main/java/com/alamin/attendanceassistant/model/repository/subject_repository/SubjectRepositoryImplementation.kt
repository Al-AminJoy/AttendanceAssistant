package com.alamin.attendanceassistant.model.repository.subject_repository

import com.alamin.attendanceassistant.model.data.Subject
import com.alamin.attendanceassistant.model.local.LocalDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubjectRepositoryImplementation @Inject constructor(private val localDatabase: LocalDatabase): SubjectLocalRepository {

    private val subjectDao = localDatabase.subjectDao()

    override fun getSubjectBySection(sectionId: Int): Flow<List<Subject>> {
        return subjectDao.getSubjectBySection(sectionId)
    }

    override fun getAll(): Flow<List<Subject>> {
        return subjectDao.getAllSubject()
    }

    override fun getById(id: Int): Flow<Subject> {
        return subjectDao.getSubjectById(id)
    }

    override suspend fun create(dataClass: Subject) {
        subjectDao.insertSection(dataClass)
    }

    override suspend fun update(dataClass: Subject) {
        subjectDao.updateSubject(dataClass)
    }

    override suspend fun delete(id: Int) {
        subjectDao.deleteSubject(id)
    }
}