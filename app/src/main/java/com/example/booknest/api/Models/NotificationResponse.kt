package com.example.booknest.api.Models

data class NotificationResponse(
    val author: String,
    val bookCover: String,
    val bookId: String,
    val bookTitle: String,
    val progress: Int,
    val status: String,
    val type: String,
    val userAvatar: String,
    val userId: Int,
    val userName: String
)