package com.example.githubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.response.FollowingViewModel
import com.example.githubuser.databinding.FragmentDetailFollowingBinding
import com.example.githubuser.ui.adapter.FollowGithubAdapter


class DetailFollowingFragment : Fragment() {
    private lateinit var bindingFollowing: FragmentDetailFollowingBinding
    private lateinit var viewModelFollowing: FollowingViewModel
    private lateinit var userAdapter: FollowGithubAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindingFollowing = FragmentDetailFollowingBinding.inflate(inflater,container,false)
        val view = bindingFollowing.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(DetailFragment.USERNAME)
        userAdapter = FollowGithubAdapter()
        userAdapter.notifyDataSetChanged()

        viewModelFollowing = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        viewModelFollowing.setUsersFollowing(username, "Tidak dapat menampilkan data karena masalah koneksi")
        bindingFollowing.apply {
            recycleViewFollowing.layoutManager = LinearLayoutManager(context)
            recycleViewFollowing.setHasFixedSize(true)
            viewModelFollowing.listUserFollowing.observe(viewLifecycleOwner) {
                userAdapter.submitList(it)
                recycleViewFollowing.adapter = userAdapter
            }
            viewModelFollowing.messageError.observe(viewLifecycleOwner) {
                showToast(it)
            }
            viewModelFollowing.isLoading.observe(viewLifecycleOwner){
                showLoading(it)
            }
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    private fun showLoading(isLoading: Boolean) {
        bindingFollowing.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}