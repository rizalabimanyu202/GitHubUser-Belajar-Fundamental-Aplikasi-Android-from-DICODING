package com.example.githubuser.data.response

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    val _listUserSearch = MutableLiveData<List<ItemsItem>>()
    val listUserSearch: LiveData<List<ItemsItem>> = _listUserSearch
    val _messageError = MutableLiveData<String>()
    val messageError: LiveData<String> = _messageError
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setUsersSearch(query: String, message: String){
        _isLoading.value = true
        ApiConfig.getApiService().getSearchUsers(query).enqueue(object : Callback<GithubUserResponse>{
            override fun onResponse(call: Call<GithubUserResponse>, response: Response<GithubUserResponse>) {
                if(response.isSuccessful){
                    _isLoading.value = false
                    _listUserSearch.value = response.body()?.items
                }
            }

            override fun onFailure(call: Call<GithubUserResponse>, tw: Throwable) {
                _messageError.setValue(message)
            }

        })
    }

}