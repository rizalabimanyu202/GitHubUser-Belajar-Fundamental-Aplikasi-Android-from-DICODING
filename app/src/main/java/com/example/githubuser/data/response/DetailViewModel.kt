package com.example.githubuser.data.response

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.retrofit.ApiConfig
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.database.FavoriteUserDao
import com.example.githubuser.database.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {

    val _detailUserSearch = MutableLiveData<DetailGithubUserResponse>()
    val detailUserSearch: LiveData<DetailGithubUserResponse> = _detailUserSearch
    val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String> = _messageError
    val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    var user = FavoriteUser()

    private var userDaoFavoriteUser: FavoriteUserDao? = null
    private var userDbFavoriteUser: UserDatabase? = null

    init{
        userDbFavoriteUser = UserDatabase.getDatabase(application)
        userDaoFavoriteUser = userDbFavoriteUser?.favoriteUserDao()
    }


    fun setDetailUsers(username: String?, message: String){
        _isLoading.value = true
        if (username != null) {
            ApiConfig.getApiService().getDetailUsers(username).enqueue(object : Callback<DetailGithubUserResponse> {
                override fun onResponse(call: Call<DetailGithubUserResponse>, response: Response<DetailGithubUserResponse>) {
                    if(response.isSuccessful){
                        _isLoading.value = false
                        _detailUserSearch.value = response.body()
                    }
                }
                override fun onFailure(call: Call<DetailGithubUserResponse>, tw: Throwable) {
                    _messageError.setValue(message)
                }

            })
        }
    }

    suspend fun checkUser(id: Int?) = userDaoFavoriteUser?.checkUser(id)

    fun addToFavorite(id: Int?, login:String?, type:String?, avatar_url:String?){
        CoroutineScope(Dispatchers.IO).launch{
            user = FavoriteUser(id, login, type, avatar_url)
            userDaoFavoriteUser?.addFavorite(user)
        }
    }

    fun removeFromFavorite(id: Int?){ CoroutineScope(Dispatchers.IO).launch{ userDaoFavoriteUser?.removeFavorite(id) } }
}