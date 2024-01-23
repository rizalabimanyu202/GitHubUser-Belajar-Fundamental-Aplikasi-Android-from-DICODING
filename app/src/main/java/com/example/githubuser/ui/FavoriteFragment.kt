package com.example.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.data.response.FavoriteViewModel
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.data.response.MainViewModel
import com.example.githubuser.database.FavoriteUser
//import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.databinding.FragmentDetailBinding
import com.example.githubuser.databinding.FragmentFavoriteBinding
import com.example.githubuser.ui.adapter.GitHubUserAdapter

class FavoriteFragment : Fragment() {

    private lateinit var bindingFavorite: FragmentFavoriteBinding
    private lateinit var userAdapter: GitHubUserAdapter
    private lateinit var viewModelFavorite: FavoriteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindingFavorite = FragmentFavoriteBinding.inflate(inflater,container,false)
        val view = bindingFavorite.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userAdapter = GitHubUserAdapter()

        viewModelFavorite = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        userAdapter.setOnItemClickCallback(object: GitHubUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem) {
                val mBundle = Bundle()
                data.id?.let {
                    mBundle.putInt(HomeFragment.EXTRA_ID, it)
                }
                mBundle.putString(HomeFragment.EXTRA_NAME, data.login)
                mBundle.putString(HomeFragment.EXTRA_TYPE, data.type)
                mBundle.putString(HomeFragment.EXTRA_URL, data.avatarUrl)
                view.findNavController().navigate(R.id.action_favoriteFragment_to_detailFragment, mBundle)
            }
        })
        bindingFavorite.apply {
            rvFavorite.layoutManager = LinearLayoutManager(context)
            rvFavorite.setHasFixedSize(true)
            viewModelFavorite.getFavoriteUser()?.observe(viewLifecycleOwner){
                val list = mappingList(it)
                userAdapter.submitList(list)
                rvFavorite.adapter = userAdapter
            }
        }
    }
    private fun mappingList(users: List<FavoriteUser>): ArrayList<ItemsItem>{
        val listUser = ArrayList<ItemsItem>()
        for(user in users){
            val userMapped = ItemsItem(user.id, user.login, user.type, user.avatar_url,)
            listUser.add(userMapped)
        }
        return listUser
    }

}