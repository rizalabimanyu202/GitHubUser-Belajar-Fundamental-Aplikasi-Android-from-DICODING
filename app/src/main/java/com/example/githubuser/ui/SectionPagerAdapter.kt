package com.example.githubuser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity, username: String?) : FragmentStateAdapter(activity) {

    var usernameGithub = username

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = DetailFollowersFragment()
            1 -> fragment = DetailFollowingFragment()
        }
        fragment?.arguments = Bundle().apply{
            putString(DetailFragment.USERNAME, usernameGithub)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}