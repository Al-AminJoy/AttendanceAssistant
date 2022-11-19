package com.alamin.attendanceassistant.model.local.dao

import androidx.room.*
import com.alamin.attendanceassistant.model.data.Subject
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSection(subject: Subject)

    @Update
    suspend fun updateSubject(subject: Subject)

    @Query("DELETE FROM Subject WHERE subjectId=:subjectId")
    suspend fun deleteSubject(subjectId:Int)

    @Query("SELECT * FROM Subject WHERE subjectId=:subjectId")
    fun getSubjectById(subjectId:Int): Flow<Subject>

    @Query("SELECT * FROM Subject WHERE sectionId=:sectionId")
    fun getSubjectBySection(sectionId:Int): Flow<List<Subject>>

    @Query("SELECT * FROM Subject")
    fun getAllSubject(): Flow<List<Subject>>
}