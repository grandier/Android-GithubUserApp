package com.bangkit.githubuserapp.favorite

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.githubuserapp.Data.local.DatabaseModule
import com.bangkit.githubuserapp.UserAdapter
import com.bangkit.githubuserapp.databinding.ActivityFavoriteBinding
import com.bangkit.githubuserapp.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val adapter by lazy { UserAdapter{ user ->
        Intent(this, DetailActivity::class.java).apply {
            putExtra("item", user)
            startActivity(this)
        }
    } }

    private val viewModel by viewModels<FavoriteViewModel>{
        FavoriteViewModel.Factory(DatabaseModule(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = adapter

        viewModel.getUserFavorite().observe(this) {
            adapter.setData(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserFavorite().observe(this) {
            adapter.setData(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}