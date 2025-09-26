package com.aakash.runningapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aakash.runningapp.data.local.dao.UserDao
import com.aakash.runningapp.data.local.entity.Converters
import com.aakash.runningapp.data.local.entity.UserEntity

@Database(version = 1, entities = [UserEntity::class], exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}

