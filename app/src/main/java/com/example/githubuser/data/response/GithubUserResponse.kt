package com.example.githubuser.data.response

import com.google.gson.annotations.SerializedName

data class GithubUserResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<ItemsItem>
)

data class ItemsItem(

	@field:SerializedName("id")
	val id: Int?,

	@field:SerializedName("login")
	val login: String?,

	@field:SerializedName("type")
	val type: String?,

	@field:SerializedName("avatar_url")
	val avatarUrl: String?,

)
