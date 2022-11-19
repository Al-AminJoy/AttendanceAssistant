package com.alamin.attendanceassistant.model.repository.class_repository

import com.alamin.attendanceassistant.model.data.ClassModel
import com.alamin.attendanceassistant.model.local.LocalDatabase
import com.alamin.attendanceassistant.model.repository.class_repository.ClassLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClassLocalRepositoryImplementation @Inject constructor(private val localDatabase: LocalDatabase):
    ClassLocalRepository {

    private val classDao = localDatabase.classDao()
    override fun getAll(): Flow<List<ClassModel>> {
        return classDao.getAllClass()
    }

    override fun getById(id: Int): Flow<ClassModel> {
        return classDao.getClassById(id)
    }

    override suspend fun create(dataModel: ClassModel) {
        classDao.insertClass(dataModel)
    }

    override suspend fun update(dataModel: ClassModel) {
        classDao.updateClass(dataModel)
    }

    override suspend fun delete(id: Int) {
        classDao.deleteClass(id)
    }


}