package com.example.githubuser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteUser::class],
    version = 2
)
abstract class UserDatabase: RoomDatabase() {
    abstract fun favoriteUserDao() :FavoriteUserDao
    companion object{
        var INSTANCE : UserDatabase? = null
        fun getDatabase(context: Context): UserDatabase?{
            if(INSTANCE==null){
                synchronized(UserDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "database_user_favorite_duf_duf").build()
                }
            }
            return INSTANCE
        }
    }
}