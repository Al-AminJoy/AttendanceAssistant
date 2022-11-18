package com.alamin.attendanceassistant.di

import android.content.Context
import androidx.room.Room
import com.alamin.attendanceassistant.model.local.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

class LocalModule {

    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            LocalDatabase::class.java,
            "local_database")
            .fallbackToDestructiveMigration()
            .build()

    }

}