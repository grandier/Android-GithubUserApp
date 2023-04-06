package com.bangkit.githubuserapp.Data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bangkit.githubuserapp.Data.model.UserGithub

@Database(entities = [UserGithub.Item::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {
    abstract fun userDao(): UserDao
}