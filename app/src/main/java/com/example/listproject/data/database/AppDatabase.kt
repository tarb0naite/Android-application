package com.example.listproject.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.listproject.data.dao.PostDAO
import com.example.listproject.data.dao.UserDAO
import com.example.listproject.data.models.PostEntity
import com.example.listproject.data.models.UserEntity

@Database(entities = [PostEntity::class, UserEntity::class], version = 1 )
abstract class AppDatabase : RoomDatabase() {

    abstract fun postDao(): PostDAO
    abstract fun userDao(): UserDAO
}