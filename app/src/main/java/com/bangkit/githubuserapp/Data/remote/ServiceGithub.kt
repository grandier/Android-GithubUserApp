package com.bangkit.githubuserapp.Data.remote

import com.bangkit.githubuserapp.BuildConfig
import com.bangkit.githubuserapp.Data.model.DetailUserGithub
import com.bangkit.githubuserapp.Data.model.UserGithub
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ServiceGithub {
    @JvmSuppressWildcards
    @GET("users")
    suspend fun getGithubUser(@Header("Authorization") authorization: String = BuildConfig.TOKEN): MutableList<UserGithub.Item>

    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getGithubUserDetail(
        @Path("username") username: String,
        @Header("Authorization") authorization: String = BuildConfig.TOKEN
    ): DetailUserGithub

    @JvmSuppressWildcards
    @GET("users/{username}/followers")
    suspend fun getGithubUserFollowers(
        @Path("username") username: String,
        @Header("Authorization") authorization: String = BuildConfig.TOKEN
    ): MutableList<UserGithub.Item>

    @JvmSuppressWildcards
    @GET("users/{username}/following")
    suspend fun getGithubUserFollowing(
        @Path("username") username: String,
        @Header("Authorization") authorization: String = BuildConfig.TOKEN
    ): MutableList<UserGithub.Item>

    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun getGithubUserSearch(
        @QueryMap param: Map<String, Any>,
        @Header("Authorization") authorization: String = BuildConfig.TOKEN
    ): UserGithub
}