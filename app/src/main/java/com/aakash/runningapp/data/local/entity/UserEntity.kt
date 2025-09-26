package com.aakash.runningapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("users")
data class UserEntity(

    @ColumnInfo(name = "id")
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true) val id: Long? = null,

    @ColumnInfo(name = "firstName")
    @SerializedName("firstName")
    val fullName: String,

    @ColumnInfo(name = "lastName")
    @SerializedName("lastName")
    val lastName: String,

    @ColumnInfo(name = "dob")
    @SerializedName("dob")
    val dob: String,

    @ColumnInfo(name = "profilePicture")
    @SerializedName("profilePicture")
    val profilePicture: ByteArray?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserEntity

        if (id != other.id) return false
        if (fullName != other.fullName) return false
        if (lastName != other.lastName) return false
        if (dob != other.dob) return false
        if (!profilePicture.contentEquals(other.profilePicture)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + fullName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + dob.hashCode()
        result = 31 * result + (profilePicture?.contentHashCode() ?: 0)
        return result
    }
}