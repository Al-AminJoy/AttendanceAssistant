package com.alamin.attendanceassistant.model.repository.student_repository

import com.alamin.attendanceassistant.model.data.Student
import com.alamin.attendanceassistant.model.repository.CommonRepository
import kotlinx.coroutines.flow.Flow

interface StudentLocalRepository : CommonRepository<Student> {
    fun getStudentBySubject(subjectId:Int): Flow<List<Student>>
}