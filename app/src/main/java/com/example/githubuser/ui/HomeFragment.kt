package com.example.githubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.data.response.MainViewModel
import com.example.githubuser.data.response.ModeViewModel
import com.example.githubuser.data.response.SettingPreferences
import com.example.githubuser.data.response.ViewModelFactory
import com.example.githubuser.data.response.dataStore
import com.example.githubuser.databinding.FragmentHomeBinding
import com.example.githubuser.ui.adapter.GitHubUserAdapter
import com.google.android.material.switchmaterial.SwitchMaterial

class HomeFragment : Fragment() {

    private var _bindingHome: FragmentHomeBinding? = null
    private val bindingHome get() = _bindingHome!!
    private lateinit var viewModelMain: MainViewModel
    private lateinit var userAdapter: GitHubUserAdapter
    companion object {
        var EXTRA_ID = "extra_id"
        var EXTRA_NAME = "extra_name"
        var EXTRA_TYPE = "extra_type"
        var EXTRA_URL = "extra_url"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _bindingHome = FragmentHomeBinding.inflate(inflater,container,false)
        val view = bindingHome.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModelMain = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        userAdapter = GitHubUserAdapter()
        userAdapter.setOnItemClickCallback(object: GitHubUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem) {
                val mBundle = Bundle()
                data.id?.let {
                    mBundle.putInt(EXTRA_ID, it)
                }
                mBundle.putString(EXTRA_NAME, data.login)
                mBundle.putString(EXTRA_TYPE, data.type)
                mBundle.putString(EXTRA_URL, data.avatarUrl)
                view.findNavController().navigate(R.id.action_homeFragment_to_detailFragment, mBundle)
            }
        })

        bindingHome.apply{
            recycleViewMain.layoutManager = LinearLayoutManager(context)
            recycleViewMain.setHasFixedSize(true)

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    if (!query.isNullOrBlank()) {
                        viewModelMain.setUsersSearch(query, "Tidak dapat menampilkan data karena masalah koneksi")
                    }
                    return true
                }
            })
            viewModelMain.listUserSearch.observe(viewLifecycleOwner, Observer {
                userAdapter.submitList(it)
                recycleViewMain.adapter = userAdapter
            })
        }
        viewModelMain.messageError.observe(viewLifecycleOwner) {
            showToast(it)
        }
        viewModelMain.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    private fun showLoading(isLoading: Boolean) {
        bindingHome.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_favorite -> findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
            R.id.menu_mode -> findNavController().navigate(R.id.action_homeFragment_to_modeFragment)
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onDestroy() {
        super.onDestroy()
        _bindingHome = null
    }
}