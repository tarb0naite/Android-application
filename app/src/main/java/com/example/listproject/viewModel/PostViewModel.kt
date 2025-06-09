package com.example.listproject.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.listproject.data.PostRepository
import com.example.listproject.data.database.DatabaseModule
import com.example.listproject.data.models.PostWithUser
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State


class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val database = DatabaseModule.getDatabase(application)
    private val repository = PostRepository(database.postDao(), database.userDao())
    private val _errorMessage = mutableStateOf<String?>(null)
    private val _isRefreshing = mutableStateOf(false)
    val errorMessage: State<String?> = _errorMessage
    val isRefreshing: State<Boolean> = _isRefreshing

    val posts = repository.getPosts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun refreshData() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                repository.refreshPosts()
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = e.localizedMessage ?: "An error occurred"
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    val postsWithUsers = repository.getPosts().combine(repository.getUsers()) { posts, users ->
        posts.map { post ->
            val userName = users.find { it.id == post.userId }?.name ?: "Unknown"
            PostWithUser(postTitle = post.title, userName = userName)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

}