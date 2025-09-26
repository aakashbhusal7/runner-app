package com.aakash.runningapp.di

import android.content.Context
import android.content.SharedPreferences
import com.aakash.runningapp.di.qualifiers.SharedPrefInfoQualifier
import com.aakash.runningapp.shared_pref.PrefConstants
import com.aakash.runningapp.shared_pref.SharedPrefHelper
import com.aakash.runningapp.shared_pref.SharedPrefHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.IOException
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {


    @Provides
    @SharedPrefInfoQualifier
    fun provideSharedPreferenceName(): String {
        return PrefConstants.PREF_FILE_NAME
    }

    @Singleton
    @Provides
    fun provideSecureSharedPreferences(
        @ApplicationContext context: Context,
        @SharedPrefInfoQualifier fileName: String
    ): SharedPreferences {
        try {
            //TODO: Implement shared preferences encryption
        } catch (e: IOException) {
            // Handle IO exception
            e.printStackTrace()
        }

        // If an exception occurs, fallback to using regular SharedPreferences
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesHelper(sharedPrefHelper: SharedPrefHelperImpl): SharedPrefHelper {
        return sharedPrefHelper
    }
}