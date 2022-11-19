package com.alamin.attendanceassistant.model.repository.section_repository

import com.alamin.attendanceassistant.model.data.ClassModel
import com.alamin.attendanceassistant.model.data.Section
import com.alamin.attendanceassistant.model.local.LocalDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SectionLocalRepositoryImplementation @Inject constructor(private val localDatabase: LocalDatabase): SectionLocalRepository {

    private val sectionDao = localDatabase.sectionDao()

    override fun getSectionByClass(classId: Int): Flow<List<Section>> {
        return sectionDao.getSectionByClass(classId)
    }

    override fun getAll(): Flow<List<Section>> {
        return sectionDao.getAllSection()
    }

    override fun getById(id: Int): Flow<Section> {
        return sectionDao.getSectionById(id)
    }

    override suspend fun create(dataClass: Section) {
        sectionDao.insertSection(dataClass)
    }

    override suspend fun update(dataClass: Section) {
        sectionDao.updateSection(dataClass)
    }

    override suspend fun delete(id: Int) {
        sectionDao.deleteSection(id)
    }


}