package com.alamin.attendanceassistant.model.repository

import com.alamin.attendanceassistant.model.data.ClassModel
import com.alamin.attendanceassistant.model.local.LocalDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClassLocalRepository @Inject constructor(private val localDatabase: LocalDatabase): Repository {

    private val classDao = localDatabase.classDao()
    override fun getAll(): Flow<List<ClassModel>> {
        return classDao.getAllClass()
    }

    override suspend fun getById(classId: Int): Flow<ClassModel> {
        return classDao.getClassById(classId)
    }

    override suspend fun create(classModel: ClassModel) {
        classDao.insertClass(classModel)
    }

    override suspend fun update(classModel: ClassModel) {
        classDao.updateClass(classModel)
    }

    override suspend fun delete(classId: Int) {
        classDao.deleteClass(classId)
    }


}