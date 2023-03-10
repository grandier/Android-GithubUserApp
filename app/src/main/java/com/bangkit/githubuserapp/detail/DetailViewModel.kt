package com.bangkit.githubuserapp.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.githubuserapp.Data.remote.ApiClient
import com.bangkit.githubuserapp.util.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel: ViewModel() {
    val resultDetailUserGithub = MutableLiveData<Result>()
    val resultFollowers = MutableLiveData<Result>()
    val resultFollowing = MutableLiveData<Result>()


    fun getDetailUser(username: String){
        viewModelScope.launch{
            flow {
                val response = ApiClient.ServiceGithub.getGithubUserDetail(username)

                emit(response)
            }.onStart {
                resultDetailUserGithub.value = Result.Loading(true)
            }.onCompletion {
                resultDetailUserGithub.value = Result.Loading(false)
            }.catch {
                resultDetailUserGithub.value = Result.Error(it)
            }.collect {
                resultDetailUserGithub.value = Result.Success(it)
            }
        }
    }

    fun getFollower(username: String){
        viewModelScope.launch{
            flow {
                val response = ApiClient.ServiceGithub.getGithubUserFollowers(username)

                emit(response)
            }.onStart {
                resultFollowers.value = Result.Loading(true)
            }.onCompletion {
                resultFollowers.value = Result.Loading(false)
            }.catch {
                resultFollowers.value = Result.Error(it)
            }.collect {
                resultFollowers.value = Result.Success(it)
            }
        }
    }

    fun getFollowing(username: String){
        viewModelScope.launch{
            flow {
                val response = ApiClient.ServiceGithub.getGithubUserFollowing(username)

                emit(response)
            }.onStart {
                resultFollowing.value = Result.Loading(true)
            }.onCompletion {
                resultFollowing.value = Result.Loading(false)
            }.catch {
                resultFollowing.value = Result.Error(it)
            }.collect {
                resultFollowing.value = Result.Success(it)
            }
        }
    }
}