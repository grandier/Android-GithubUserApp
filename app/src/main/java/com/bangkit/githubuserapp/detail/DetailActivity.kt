package com.bangkit.githubuserapp.detail

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.bangkit.githubuserapp.Data.local.DatabaseModule
import com.bangkit.githubuserapp.Data.model.DetailUserGithub
import com.bangkit.githubuserapp.Data.model.UserGithub
import com.bangkit.githubuserapp.R
import com.bangkit.githubuserapp.databinding.ActivityDetailBinding
import com.bangkit.githubuserapp.detail.follow.FollowFragment
import com.bangkit.githubuserapp.util.Result
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>() {
        DetailViewModel.Factory(DatabaseModule(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val item = intent.getParcelableExtra<UserGithub.Item>("item")
        val username = item?.login ?: ""
        viewModel.getDetailUser(username)
        viewModel.resultDetailUserGithub.observe(this) {
            when (it) {
                is Result.Success<*> -> {
                    val user = it.data as DetailUserGithub
                    binding.detailImage.load(user.avatar_url){
                        transformations(CircleCropTransformation())
                    }
                    binding.detailName.text = user.name
                    binding.detailUsername.text = user.login
                    binding.detailFollowersNumber.text = user.followers.toString()
                    binding.detailFollowingNumber.text = user.following.toString()
                }
                is Result.Error -> {
                    Toast.makeText(this, it.exception.message, Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    binding.progressBarDetail.visibility =
                        if (it.isLoading) android.view.View.VISIBLE else android.view.View.GONE
                }
            }
        }

        // load follower and following data
        viewModel.getFollower(username)
        viewModel.resultFollowers.observe(this) {

        }
        viewModel.getFollowing(username)
        viewModel.resultFollowing.observe(this) {
            // handle result
        }

        val fragment = mutableListOf<Fragment>(
            FollowFragment.newInstance(FollowFragment.FOLLOWERS),
            FollowFragment.newInstance(FollowFragment.FOLLOWING)
        )

        val titleFragment = mutableListOf(
            getString(R.string.followers),
            getString(R.string.following)
        )

        val adapter = DetailAdapter(this, fragment)
        binding.detailViewpager.adapter = adapter

        TabLayoutMediator(binding.detailTable, binding.detailViewpager) { tab, position ->
            tab.text = titleFragment[position]
        }.attach()

        binding.detailTable.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.position == 0){
                    viewModel.getFollower(username)
                }else
                    viewModel.getFollowing(username)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        binding.btnFavorite.setOnClickListener {
            viewModel.setFavorite(item ?: return@setOnClickListener)
        }

        viewModel.findFavorite(item?.id ?: 0){
            binding.btnFavorite.changeIconColor(R.color.red)
        }

        viewModel.resultFav.observe(this){
            binding.btnFavorite.changeIconColor(R.color.red)
        }

        viewModel.deleteFav.observe(this){
            binding.btnFavorite.changeIconColor(R.color.white)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

fun FloatingActionButton.changeIconColor(@ColorRes color: Int){
    imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
}