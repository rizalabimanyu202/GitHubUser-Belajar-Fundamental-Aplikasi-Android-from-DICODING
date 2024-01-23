package com.example.githubuser.data.response

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    val _listUserFollowing = MutableLiveData<List<FollowGithubUserResponseItem?>?>()
    val listUserFollowing: LiveData<List<FollowGithubUserResponseItem?>?> = _listUserFollowing
    val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String> = _messageError
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setUsersFollowing(username: String?, message: String){
        _isLoading.value = true
        if (username != null) {
            ApiConfig.getApiService().getFollowingUsers(username).enqueue(object : Callback<FollowGithubUserResponse> {
                override fun onResponse(call: Call<FollowGithubUserResponse>, response: Response<FollowGithubUserResponse>) {
                    if(response.isSuccessful){
                        _isLoading.value = false
                        _listUserFollowing.value = response.body()
                    }
                }
                override fun onFailure(call: Call<FollowGithubUserResponse>, tw: Throwable) {
                    _messageError.setValue(message)
                }
            })
        }
    }
}