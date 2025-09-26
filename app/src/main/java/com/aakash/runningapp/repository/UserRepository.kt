package com.aakash.runningapp.repository

import com.aakash.runningapp.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun saveUser(userEntity: UserEntity): Long

    fun getUserById(id: Long): Flow<UserEntity?>
}