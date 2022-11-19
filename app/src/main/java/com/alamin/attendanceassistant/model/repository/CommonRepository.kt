package com.alamin.attendanceassistant.model.repository

import com.alamin.attendanceassistant.model.data.ClassModel
import kotlinx.coroutines.flow.Flow

interface CommonRepository<T> {
    fun getAll(): Flow<List<T>>
    fun getById(id:Int): Flow<T>
    suspend fun create(dataClass: T)
    suspend fun update(dataClass: T)
    suspend fun delete(id: Int)
}