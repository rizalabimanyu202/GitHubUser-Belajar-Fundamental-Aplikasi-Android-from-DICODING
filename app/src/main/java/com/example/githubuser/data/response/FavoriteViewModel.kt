package com.example.githubuser.data.response

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.database.FavoriteUserDao
import com.example.githubuser.database.UserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDaoFavoriteUser: FavoriteUserDao? = null
    private var userDbFavoriteUser: UserDatabase? = null

    init{
        userDbFavoriteUser = UserDatabase.getDatabase(application)
        userDaoFavoriteUser = userDbFavoriteUser?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>?{
        return userDaoFavoriteUser?.getFavoriteUser()
    }
}