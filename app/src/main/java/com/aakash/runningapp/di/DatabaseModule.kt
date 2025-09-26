package com.aakash.runningapp.di

import android.app.Application
import androidx.room.Room
import com.aakash.runningapp.data.local.dao.UserDao
import com.aakash.runningapp.data.local.database.AppDatabase
import com.aakash.runningapp.repository.UserRepository
import com.aakash.runningapp.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                "runner_db"
            )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.getUserDao()
    }


    @Provides
    fun provideUserRepository(userDao: UserDao): UserRepository = UserRepositoryImpl(userDao)
}