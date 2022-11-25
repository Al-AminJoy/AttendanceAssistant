package com.alamin.attendanceassistant.di

import com.alamin.attendanceassistant.di.qualifiers.*
import com.alamin.attendanceassistant.model.repository.attendance_repository.AttendanceLocalRepository
import com.alamin.attendanceassistant.model.repository.attendance_repository.AttendanceRepositoryImplementation
import com.alamin.attendanceassistant.model.repository.class_repository.ClassLocalRepository
import com.alamin.attendanceassistant.model.repository.class_repository.ClassLocalRepositoryImplementation
import com.alamin.attendanceassistant.model.repository.section_repository.SectionLocalRepository
import com.alamin.attendanceassistant.model.repository.section_repository.SectionLocalRepositoryImplementation
import com.alamin.attendanceassistant.model.repository.student_repository.StudentLocalRepository
import com.alamin.attendanceassistant.model.repository.student_repository.StudentLocalRepositoryImplementation
import com.alamin.attendanceassistant.model.repository.subject_repository.SubjectLocalRepository
import com.alamin.attendanceassistant.model.repository.subject_repository.SubjectRepositoryImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryProvider {

    @Binds
    @ClassLocalQualifier
    abstract fun classLocalRepositoryBind(classRepository: ClassLocalRepositoryImplementation): ClassLocalRepository

    @Binds
    @SectionLocalQualifier
    abstract fun sectionLocalRepositoryBind(sectionLocalRepositoryImplementation: SectionLocalRepositoryImplementation): SectionLocalRepository

    @Binds
    @SubjectLocalQualifier
    abstract fun subjectLocalRepositoryBind(subjectRepositoryImplementation: SubjectRepositoryImplementation): SubjectLocalRepository

    @Binds
    @AttendanceLocalQualifier
    abstract fun attendanceLocalRepositoryBind(attendanceRepositoryImplementation: AttendanceRepositoryImplementation): AttendanceLocalRepository

   /* @Binds
    @StudentLocalQualifier
    abstract fun studentLocalRepositoryBind(studentLocalRepositoryImplementation: StudentLocalRepositoryImplementation): StudentLocalRepository*/
}