package com.example.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.response.FollowersViewModel
import com.example.githubuser.databinding.FragmentDetailFollowersBinding
import com.example.githubuser.ui.adapter.FollowGithubAdapter

class DetailFollowersFragment : Fragment() {

    private lateinit var bindingFollowers: FragmentDetailFollowersBinding
    private lateinit var viewModelFollowers: FollowersViewModel
    private lateinit var userAdapter: FollowGithubAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindingFollowers = FragmentDetailFollowersBinding.inflate(inflater,container,false)
        val view = bindingFollowers.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(DetailFragment.USERNAME)
        userAdapter = FollowGithubAdapter()
        userAdapter.notifyDataSetChanged()

        viewModelFollowers = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        viewModelFollowers.setUsersFollowers(username, "Tidak dapat menampilkan data karena masalah koneksi")
        bindingFollowers.apply {
            recycleViewFollowers.layoutManager = LinearLayoutManager(context)
            recycleViewFollowers.setHasFixedSize(true)
            viewModelFollowers.listUserFollowers.observe(viewLifecycleOwner) {
                userAdapter.submitList(it)
                recycleViewFollowers.adapter = userAdapter
            }
        }
        viewModelFollowers.messageError.observe(viewLifecycleOwner) {
            showToast(it)
        }
        viewModelFollowers.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    private fun showLoading(isLoading: Boolean) {
        bindingFollowers.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}