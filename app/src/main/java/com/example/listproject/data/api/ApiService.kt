package com.example.listproject.data.api

import com.example.listproject.data.models.PostEntity
import com.example.listproject.data.models.UserEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): List <PostEntity>

    @GET("users")
    suspend fun getUser(): List<UserEntity>


}