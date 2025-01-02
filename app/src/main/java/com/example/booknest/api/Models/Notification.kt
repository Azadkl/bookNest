package com.example.booknest.api.Models

data class Notification(
    val userId: Int,
    val bookId: String,
    val text: String
)