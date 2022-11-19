package com.alamin.attendanceassistant.model.local.dao

import androidx.room.*
import com.alamin.attendanceassistant.model.data.Section
import kotlinx.coroutines.flow.Flow

@Dao
interface SectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSection(section: Section)

    @Update
    suspend fun updateSection(section: Section)

    @Query("DELETE FROM Section WHERE sectionId=:sectionId")
    suspend fun deleteSection(sectionId:Int)

    @Query("SELECT * FROM Section WHERE sectionId=:sectionId")
    fun getSectionById(sectionId:Int): Flow<Section>

    @Query("SELECT * FROM Section WHERE classId=:classId")
    fun getSectionByClass(classId:Int): Flow<List<Section>>

    @Query("SELECT * FROM Section")
    fun getAllSection(): Flow<List<Section>>
}