package com.example.booknest.api.Models

data class Review(
    val bookId: String,
    val rating: Float,
    val text: String,
    val date:Long,
    val avatar:String,
    val username: String
)