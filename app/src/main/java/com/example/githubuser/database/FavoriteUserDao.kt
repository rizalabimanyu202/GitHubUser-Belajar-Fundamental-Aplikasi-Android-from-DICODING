package com.example.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert
    suspend fun addFavorite(favoriteUser: FavoriteUser)

    @Query("select * from favorite_user")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("select count(*) from favorite_user where favorite_user.id = :id")
    suspend fun checkUser(id: Int?): Int

    @Query("delete from favorite_user where favorite_user.id = :id")
    suspend fun removeFavorite(id: Int?): Int
}