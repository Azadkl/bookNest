package com.example.booknest.api.Models

data class BookProgress(
    val BookId: String,
    val Status: String,
    val Progress: Int
)