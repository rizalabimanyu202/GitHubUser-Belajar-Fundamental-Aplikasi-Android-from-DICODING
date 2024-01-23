package com.example.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.databinding.GithubItemBinding

class GitHubUserAdapter : ListAdapter<ItemsItem, GitHubUserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var onItemClickCallbackX: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallbackX: OnItemClickCallback){
        this.onItemClickCallbackX = onItemClickCallbackX
    }

    class MyViewHolder(val binding: GithubItemBinding, private var onItemClickCallbackX: OnItemClickCallback?) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: ItemsItem){
            binding.root.setOnClickListener{
                onItemClickCallbackX?.onItemClicked(user)
            }
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
        return MyViewHolder(binding, onItemClickCallbackX)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: ItemsItem)
    }
}
