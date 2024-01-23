package com.example.githubuser.data.response

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {
    val _listUserFollowers = MutableLiveData<List<FollowGithubUserResponseItem?>?>()
    val listUserFollowers: LiveData<List<FollowGithubUserResponseItem?>?> = _listUserFollowers
    val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String> = _messageError
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setUsersFollowers(username: String?, message: String){
        _isLoading.value = true
        if (username != null) {
            ApiConfig.getApiService().getFollowersUsers(username).enqueue(object : Callback<FollowGithubUserResponse> {
                override fun onResponse(call: Call<FollowGithubUserResponse>, response: Response<FollowGithubUserResponse>) {
                    if(response.isSuccessful){
                        _isLoading.value = false
                        _listUserFollowers.value = response.body()
                    }
                }
                override fun onFailure(call: Call<FollowGithubUserResponse>, tw: Throwable) {
                    _messageError.setValue(message)
                }
            })
        }
    }
}