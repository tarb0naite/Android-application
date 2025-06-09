package com.example.listproject.data

import com.example.listproject.data.api.ApiClient
import com.example.listproject.data.dao.PostDAO
import com.example.listproject.data.dao.UserDAO
import com.example.listproject.data.models.PostEntity
import com.example.listproject.data.models.UserEntity
import kotlinx.coroutines.flow.Flow

class PostRepository (
    private val postDao: PostDAO,
    private val userDao: UserDAO

) {
    fun getPosts(): Flow<List<PostEntity>> = postDao.getAllPosts()
    fun getUsers(): Flow<List<UserEntity>> = userDao.getAllUsers()

    suspend fun refreshPosts() {
        val posts = ApiClient.api.getPosts()

        val users = ApiClient.api.getUser()

        postDao.deleteAllPosts()
        userDao.deleteAllUsers()
        postDao.insertPosts(posts)
        userDao.insertUsers(users)
    }


}



