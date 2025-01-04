package com.example.booknest.api.Models

data class Book(
    val authorId: Int,
    val cover: String,
    val description: String,
    val isbn: String,
    val pages: Int,
    val title: String,
    val rating: Float
)