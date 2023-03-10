package com.bangkit.githubuserapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.githubuserapp.Data.model.UserGithub
import com.bangkit.githubuserapp.databinding.ActivityMainBinding
import com.bangkit.githubuserapp.detail.DetailActivity
import com.bangkit.githubuserapp.util.Result

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { UserAdapter{ user ->
        Intent(this, DetailActivity::class.java).apply {
            putExtra("username", user.login)
            startActivity(this)
        }
    } }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}