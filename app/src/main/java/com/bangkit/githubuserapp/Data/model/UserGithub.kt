package com.bangkit.githubuserapp.Data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class UserGithub(
    val incomplete_results: Boolean,
    val items: MutableList<Item>,
    val total_count: Int
) {
    @Parcelize
    @Entity(tableName = "user_github")
    data class Item(
        @ColumnInfo(name = "avatar_url")
        val avatar_url: String,
        @PrimaryKey
        val id: Int,
        @ColumnInfo(name = "login")
        val login: String,
    ) : Parcelable
}
