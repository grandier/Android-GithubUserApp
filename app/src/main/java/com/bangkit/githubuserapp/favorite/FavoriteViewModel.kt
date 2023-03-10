package com.bangkit.githubuserapp.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.githubuserapp.Data.local.DatabaseModule

class FavoriteViewModel(private val dbModule: DatabaseModule): ViewModel() {

    fun getUserFavorite() = dbModule.userDao.loadAll()

    class Factory(private val dbModule: DatabaseModule): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavoriteViewModel(dbModule) as T
        }
    }
}