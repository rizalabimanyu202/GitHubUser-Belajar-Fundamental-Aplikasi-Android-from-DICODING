package com.example.githubuser.data.retrofit
import com.example.githubuser.data.response.DetailGithubUserResponse
import com.example.githubuser.data.response.FollowGithubUserResponse
import com.example.githubuser.data.response.GithubUserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<GithubUserResponse>

    @GET("users/{username}")
    fun getDetailUsers(
        @Path("username") username: String
    ): Call<DetailGithubUserResponse>

    @GET("users/{username}/followers")
    fun getFollowersUsers(
        @Path("username") username: String
    ): Call<FollowGithubUserResponse>

    @GET("users/{username}/following")
    fun getFollowingUsers(
        @Path("username") username: String
    ): Call<FollowGithubUserResponse>
}