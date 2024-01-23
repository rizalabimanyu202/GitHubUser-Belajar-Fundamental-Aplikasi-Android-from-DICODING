package com.example.githubuser.ui
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.githubuser.R
import com.example.githubuser.data.response.ModeViewModel
import com.example.githubuser.data.response.SettingPreferences
import com.example.githubuser.data.response.ViewModelFactory
import com.example.githubuser.data.response.dataStore
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.findNavController()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}





















//binding.recycleViewMain.layoutManager = LinearLayoutManager(this)
//binding.recycleViewMain.setHasFixedSize(true)

//getAllData()
/*private fun getAllData(){
        ApiConfig.getApiService().getRestaurant().enqueue(object : Callback<List<GithubResponseItem>> {
            override fun onResponse(call: Call<List<GithubResponseItem>>, response: Response<List<GithubResponseItem>>) {
                binding.recycleViewMain.adapter = ReviewAdapter(response.body()!!)
            }
            override fun onFailure(call: Call<List<GithubResponseItem>>, ta: Throwable) {
                println("fdgdgdgdfgdfgdgdgdgdgdgdgdgdgdg")
            }
        })
    }*/
