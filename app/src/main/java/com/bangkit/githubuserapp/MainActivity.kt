package com.bangkit.githubuserapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.githubuserapp.Data.local.SettingPreferences
import com.bangkit.githubuserapp.Data.model.UserGithub
import com.bangkit.githubuserapp.databinding.ActivityMainBinding
import com.bangkit.githubuserapp.detail.DetailActivity
import com.bangkit.githubuserapp.favorite.FavoriteActivity
import com.bangkit.githubuserapp.setting.SettingActivity
import com.bangkit.githubuserapp.util.Result

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { UserAdapter{ user ->
        Intent(this, DetailActivity::class.java).apply {
            putExtra("item", user)
            startActivity(this)
        }
    } }

    private val viewModel by viewModels<MainViewModel>() {
        MainViewModel.Factory(SettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getTheme().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.getUser(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        viewModel.getUser()
        viewModel.resultUserGithub.observe(this) {
            when (it) {
                is Result.Success<*> -> {
                    adapter.setData(it.data as MutableList<UserGithub.Item>)
                }
                is Result.Error -> {
                    Toast.makeText(this, it.exception.message, Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    binding.progressBar.visibility =
                        if (it.isLoading) android.view.View.VISIBLE else android.view.View.GONE
                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUser()
    }

}