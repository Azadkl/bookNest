package com.example.booknest.api.Models

data class Book(
    val author: String,
    val category: String,
    val cover: String,
    val description: String,
    val isbn: String,
    val language: String,
    val pages: Int,
    val publishedDate: String,
    val publisher: String,
    val rating: Float,
    val title: String
)
data class PostBook(
    val isbn: String
)
