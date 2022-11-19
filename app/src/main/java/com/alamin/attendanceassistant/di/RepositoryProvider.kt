package com.alamin.attendanceassistant.di

import com.alamin.attendanceassistant.di.qualifiers.ClassLocalQualifier
import com.alamin.attendanceassistant.di.qualifiers.SectionLocalQualifier
import com.alamin.attendanceassistant.model.repository.class_repository.ClassLocalRepository
import com.alamin.attendanceassistant.model.repository.class_repository.ClassLocalRepositoryImplementation
import com.alamin.attendanceassistant.model.repository.section_repository.SectionLocalRepository
import com.alamin.attendanceassistant.model.repository.section_repository.SectionLocalRepositoryImplementation
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
}