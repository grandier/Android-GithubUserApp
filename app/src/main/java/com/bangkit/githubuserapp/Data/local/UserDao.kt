package com.bangkit.githubuserapp.Data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bangkit.githubuserapp.Data.model.UserGithub

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserGithub.Item)

    @Query("SELECT * FROM user_github")
    fun loadAll(): LiveData<MutableList<UserGithub.Item>>

    @Query("SELECT * FROM user_github WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): UserGithub.Item

    @Delete
    fun delete(user: UserGithub.Item)
}