package com.aakash.runningapp.data.local.entity

import android.util.Base64
import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromByteArray(bytes: ByteArray?): String? =
        bytes?.let { Base64.encodeToString(it, Base64.DEFAULT) }

    @TypeConverter
    fun toByteArray(data: String?): ByteArray? =
        data?.let { Base64.decode(it, Base64.DEFAULT) }
}
