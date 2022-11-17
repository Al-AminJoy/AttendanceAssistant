package com.alamin.attendanceassistant.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.alamin.attendanceassistant.model.data.Converters
import com.alamin.attendanceassistant.model.data.User
import com.alamin.attendanceassistant.model.local.dao.UserDao

@Database(entities = [User::class], version = 1, exportSchema = false)
//@TypeConverter(Converters::class)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao;
}