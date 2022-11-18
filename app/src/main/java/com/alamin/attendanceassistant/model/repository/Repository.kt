package com.alamin.attendanceassistant.model.repository

import com.alamin.attendanceassistant.model.data.ClassModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getAll(): Flow<List<ClassModel>>
    suspend fun getById(classId:Int): Flow<ClassModel>
    suspend fun create(classModel: ClassModel)
    suspend fun update(classModel: ClassModel)
    suspend fun delete(classId: Int)
}