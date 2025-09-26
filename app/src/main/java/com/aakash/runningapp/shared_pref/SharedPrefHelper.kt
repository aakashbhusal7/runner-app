package com.aakash.runningapp.shared_pref

interface SharedPrefHelper {

    suspend fun saveLoggedInUserId(userId: Long,registrationStatus: Boolean)
    fun getLoggedInUserId(): Long?

    suspend fun saveRegistrationStatus(status: Boolean)

    fun getRegistrationStatus(): Boolean

}