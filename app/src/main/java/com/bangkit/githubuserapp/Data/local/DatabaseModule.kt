package com.bangkit.githubuserapp.Data.local

import android.content.Context
import androidx.room.Room

class DatabaseModule(private val context: Context) {
    private val db = Room.databaseBuilder(context, AppDB::class.java, "usergithub.db").allowMainThreadQueries().build()

    val userDao = db.userDao()
}