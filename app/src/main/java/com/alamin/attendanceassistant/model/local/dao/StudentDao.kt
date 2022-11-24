package com.alamin.attendanceassistant.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.alamin.attendanceassistant.model.data.Student
import kotlinx.coroutines.flow.Flow

interface StudentDao {
  /*  @Insert
    suspend fun insertStudent(student: Student)

    @Update
    suspend fun updateStudent(student: Student)

    @Query("DELETE FROM STUDENT WHERE studentId=:studentId")
    suspend fun deleteStudent(studentId:Int)

    @Query("SELECT * FROM Student WHERE studentId=:studentId")
    fun getStudentById(studentId: Int): Flow<Student>

    @Query("SELECT * FROM Student WHERE subjectId=:subjectId")
    fun getStudentBySubject(subjectId: Int): Flow<List<Student>>

    @Query("SELECT * FROM Student")
    fun getAllStudent(): Flow<List<Student>>*/
}