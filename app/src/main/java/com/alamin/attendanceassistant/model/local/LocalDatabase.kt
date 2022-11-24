package com.alamin.attendanceassistant.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alamin.attendanceassistant.model.data.*
import com.alamin.attendanceassistant.model.local.dao.*

@Database(entities = [User::class,ClassModel::class,Section::class,Subject::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun classDao(): ClassDao
    abstract fun sectionDao(): SectionDao
    abstract fun subjectDao(): SubjectDao
    //abstract fun studentDao(): StudentDao

}