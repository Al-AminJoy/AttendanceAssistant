package com.alamin.attendanceassistant.model.repository.student_repository

import com.alamin.attendanceassistant.model.data.Student
import com.alamin.attendanceassistant.model.local.LocalDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StudentLocalRepositoryImplementation  constructor(private val localDatabase: LocalDatabase) {

    /*private val studentDao = localDatabase.studentDao()

    override fun getStudentBySubject(subjectId: Int): Flow<List<Student>> {
        return studentDao.getStudentBySubject(subjectId)
    }

    override fun getAll(): Flow<List<Student>> {
       return studentDao.getAllStudent()
    }

    override fun getById(id: Int): Flow<Student> {
        return studentDao.getStudentById(id)
    }

    override suspend fun create(dataClass: Student) {
        studentDao.insertStudent(dataClass)
    }

    override suspend fun update(dataClass: Student) {
        studentDao.updateStudent(dataClass)
    }

    override suspend fun delete(id: Int) {
        studentDao.deleteStudent(id)
    }*/
}