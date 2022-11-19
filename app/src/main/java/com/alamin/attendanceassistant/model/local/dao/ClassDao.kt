package com.alamin.attendanceassistant.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alamin.attendanceassistant.model.data.ClassModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassDao {
   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertClass(classModel: ClassModel)

   @Update
   suspend fun updateClass(classModel: ClassModel)

   @Query("DELETE FROM ClassModel WHERE classId=:classId")
   suspend fun deleteClass(classId:Int)

   @Query("SELECT * FROM ClassModel WHERE classId=:classId")
   fun getClassById(classId:Int): Flow<ClassModel>

   @Query("SELECT * FROM ClassModel")
   fun getAllClass(): Flow<List<ClassModel>>
}