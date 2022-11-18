package com.alamin.attendanceassistant.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.alamin.attendanceassistant.model.data.*
import com.alamin.attendanceassistant.model.local.dao.*

@Database(entities = [User::class,ClassModel::class,Section::class,Subject::class,Student::class], version = 1, exportSchema = false)
//@TypeConverter(Converters::class)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun classDao(): ClassDao
    abstract fun sectionDao(): SectionDao
    abstract fun subjectDao(): SubjectDao
    abstract fun studentDao(): StudentDao

}