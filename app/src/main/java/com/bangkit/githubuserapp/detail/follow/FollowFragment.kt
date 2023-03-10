package com.bangkit.githubuserapp.detail.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.githubuserapp.Data.model.UserGithub
import com.bangkit.githubuserapp.UserAdapter
import com.bangkit.githubuserapp.databinding.FragmentFollowBinding
import com.bangkit.githubuserapp.detail.DetailViewModel
import com.bangkit.githubuserapp.util.Result

class FollowFragment : Fragment() {

    private var binding: FragmentFollowBinding? = null
    private val adapter by lazy { UserAdapter{

    } }

    private val viewModel by activityViewModels<DetailViewModel>()
    var type = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvFollow?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowFragment.adapter
        }

        when(type){
            FOLLOWERS -> {
                viewModel.resultFollowers.observe(viewLifecycleOwner, this::manageResultFollows)
            }
            FOLLOWING -> {
                viewModel.resultFollowing.observe(viewLifecycleOwner, this::manageResultFollows)
            }
        }
    }

    private fun manageResultFollows(result: Result){
        when(result){
            is Result.Success<*> -> {
                adapter.setData(result.data as MutableList<UserGithub.Item>)
            }
            is Result.Error -> {
                Toast.makeText(requireActivity(), result.exception.message, Toast.LENGTH_SHORT).show()
            }
            is Result.Loading -> {
                binding?.progressBarFragment?.isVisible = result.isLoading
            }
        }
    }

    companion object {
        const val FOLLOWING = 100
        const val FOLLOWERS = 101
        fun newInstance(type: Int) = FollowFragment().apply {
            this.type = type
        }
    }
}