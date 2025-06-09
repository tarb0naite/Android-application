package com.example.listproject.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
   @PrimaryKey val id: Int,
    val userId : Int,
    val title : String,
    val body : String
)
