package com.alamin.attendanceassistant.di

import com.alamin.attendanceassistant.di.qualifiers.ClassQualifier
import com.alamin.attendanceassistant.model.repository.Repository
import com.alamin.attendanceassistant.model.repository.ClassLocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryProvider {

    @Binds
    @ClassQualifier
    abstract fun classRepositoryBind(classRepository: ClassLocalRepository): Repository
}