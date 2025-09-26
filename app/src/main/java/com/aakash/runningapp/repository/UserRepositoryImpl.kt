package com.aakash.runningapp.repository

import com.aakash.runningapp.data.local.dao.UserDao
import com.aakash.runningapp.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDao: UserDao) : UserRepository {
    override suspend fun saveUser(userEntity: UserEntity): Long {
        return userDao.insertUser(userEntity)
    }

    override fun getUserById(id: Long): Flow<UserEntity?> {
        return userDao.getUserById(id)
    }
}