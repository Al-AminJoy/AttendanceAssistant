package com.alamin.attendanceassistant.view_model

import com.alamin.attendanceassistant.di.qualifiers.StudentLocalQualifier
import com.alamin.attendanceassistant.model.repository.student_repository.StudentLocalRepository
import javax.inject.Inject

class StudentViewModel @Inject constructor(@StudentLocalQualifier private val studentLocalRepository: StudentLocalRepository) {


}