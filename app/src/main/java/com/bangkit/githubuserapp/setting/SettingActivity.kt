package com.bangkit.githubuserapp.setting

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bangkit.githubuserapp.Data.local.SettingPreferences
import com.bangkit.githubuserapp.R
import com.bangkit.githubuserapp.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val viewModel by viewModels<SettingViewModel> {
        SettingViewModel.Factory(SettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getTheme().observe(this) {
            if (it) {
                binding.switchTheme.text = getString(R.string.dark_theme)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
            } else {
                binding.switchTheme.text = getString(R.string.light_theme)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
            }
            binding.switchTheme.isChecked = it
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveTheme(isChecked)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}