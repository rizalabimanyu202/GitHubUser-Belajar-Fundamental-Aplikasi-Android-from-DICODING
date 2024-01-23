package com.example.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.data.response.DetailViewModel
import com.example.githubuser.databinding.FragmentDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFragment : Fragment() {

    private lateinit var appCompatActivity: AppCompatActivity
    private lateinit var bindingDetail: FragmentDetailBinding
    private lateinit var viewModelDetail: DetailViewModel
    private var _isChecked = false

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val USERNAME = "username"
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindingDetail = FragmentDetailBinding.inflate(inflater,container,false)
        val view = bindingDetail.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt(HomeFragment.EXTRA_ID, 0)
        val username = arguments?.getString(HomeFragment.EXTRA_NAME)
        val type = arguments?.getString(HomeFragment.EXTRA_TYPE)
        val avatarUrl = arguments?.getString(HomeFragment.EXTRA_URL)


        appCompatActivity = requireActivity() as AppCompatActivity
        val sectionsPagerAdapter = SectionsPagerAdapter(appCompatActivity, username)
        val viewPager: ViewPager2 = view.findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = view.findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        appCompatActivity.supportActionBar?.elevation = 0f


        viewModelDetail = ViewModelProvider(this)[DetailViewModel::class.java]
        viewModelDetail.setDetailUsers(username, "Tidak dapat menampilkan data karena masalah koneksi")
        viewModelDetail.detailUserSearch.observe(viewLifecycleOwner) {
            when (it) {
                null -> Toast.makeText(requireContext(), "ERROR!!!", Toast.LENGTH_SHORT).show()
                else ->
                    bindingDetail.apply {
                        tvUsernameDetail.text = it.login
                        tvNameDetail.text = it.name
                        numberFollower.text = it.followers.toString()
                        numberFollowing.text = it.following.toString()
                        Glide.with(this@DetailFragment)
                            .load(it.avatarUrl)
                            .into(ivDetail)
                    }
            }
        }
        CoroutineScope(Dispatchers.IO).launch{
            val count = viewModelDetail.checkUser(id)
            withContext(Dispatchers.Main){
                when(count){
                    null -> Toast.makeText(requireContext(), "ERROR!!!", Toast.LENGTH_SHORT).show()
                    else ->
                        if(count>0){
                            bindingDetail.fabFavorite.isChecked = true
                            _isChecked = true
                        }else{
                            bindingDetail.fabFavorite.isChecked = false
                            _isChecked = false
                        }
                }
            }

        }
        bindingDetail.fabFavorite.setOnClickListener{
            _isChecked = !_isChecked
            when(_isChecked){
                true -> viewModelDetail.addToFavorite(id, username, type, avatarUrl)
                false -> viewModelDetail.removeFromFavorite(id)
            }
            bindingDetail.fabFavorite.isChecked = _isChecked
        }
        viewModelDetail.messageError.observe(viewLifecycleOwner) {
            showToast(it)
        }
        viewModelDetail.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    private fun showLoading(isLoading: Boolean) {
        bindingDetail.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}