package com.example.githubuser.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName =  "favorite_user")
@Parcelize
data class FavoriteUser(
    @PrimaryKey
    val id: Int? = null,
    val login: String? = null,
    val type: String? = null,
    val avatar_url: String? = null
) : Parcelable