package com.bangkit.githubuserapp

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

class MainViewModel: ViewModel() {

    val resultUserGithub = MutableLiveData<Result>()


    fun getUser(){
        viewModelScope.launch{
            flow {
                val response = ApiClient.ServiceGithub.getGithubUser()

                emit(response)
            }.onStart {
                resultUserGithub.value = Result.Loading(true)
            }.onCompletion {
                resultUserGithub.value = Result.Loading(false)
            }.catch {
                resultUserGithub.value = Result.Error(it)
            }.collect {
                resultUserGithub.value = Result.Success(it)
            }
        }
    }

    fun getUser(username: String){
        viewModelScope.launch{
            flow {
                val response = ApiClient.ServiceGithub.getGithubUserSearch(mapOf("q" to username))

                emit(response)
            }.onStart {
                resultUserGithub.value = Result.Loading(true)
            }.onCompletion {
                resultUserGithub.value = Result.Loading(false)
            }.catch {
                resultUserGithub.value = Result.Error(it)
            }.collect {
                resultUserGithub.value = Result.Success(it.items)
            }
        }
    }
}