package com.aakash.runningapp.shared_pref

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class SharedPrefHelperImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPrefHelper {
    override suspend fun saveLoggedInUserId(userId: Long,registrationStatus: Boolean) {
        sharedPreferences.edit { putLong(PrefConstants.PREF_USER_ID, userId) }
        saveRegistrationStatus(registrationStatus)
    }

    override fun getLoggedInUserId(): Long? {
        return sharedPreferences.getLong(PrefConstants.PREF_USER_ID, -1)
    }

    override fun getRegistrationStatus(): Boolean {
        return sharedPreferences.getBoolean(PrefConstants.PREF_REGISTRATION_STATUS, false)
    }

    override suspend fun saveRegistrationStatus(status: Boolean) {
        sharedPreferences.edit {
            ;
            putBoolean(PrefConstants.PREF_REGISTRATION_STATUS, status)
        };
    }
}