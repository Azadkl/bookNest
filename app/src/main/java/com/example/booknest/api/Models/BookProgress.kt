package com.example.booknest.api.Models

data class BookProgress(
    val bookId: String,
    val status: String,
    val progress: Int,
    val cover : String,
    val title: String
)
data class MybooksList(
    val reading: List<BookProgress>,
    var read: List<BookProgress>,
    val wantToRead :List<BookProgress>
)

data class PostBookProgress(
    val BookId: String,
    val Status: String,
    val Progress: Int
)