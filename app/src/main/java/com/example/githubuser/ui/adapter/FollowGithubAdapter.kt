package com.example.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.response.FollowGithubUserResponseItem
import com.example.githubuser.databinding.GithubItemBinding

class FollowGithubAdapter: ListAdapter<FollowGithubUserResponseItem, FollowGithubAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(val binding: GithubItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FollowGithubUserResponseItem){
            binding.apply {
                usernameGH.text = user.login
                typeGH.text = "Type: ${user.type}"
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .into(imageGH)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = GithubItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowGithubUserResponseItem>() {
            override fun areItemsTheSame(oldItem: FollowGithubUserResponseItem, newItem: FollowGithubUserResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FollowGithubUserResponseItem, newItem: FollowGithubUserResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}