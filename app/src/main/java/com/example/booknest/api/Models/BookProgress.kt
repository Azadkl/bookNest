package com.example.booknest.api.Models

data class BookProgress(
    val BookId: String,
    val Status: String,
    val Progress: Int,
    val Cover : String,
    val title: String
)
data class MybooksList(
    val reading: List<BookProgress>,
    var read: List<BookProgress>,
    val wantToRead :List<BookProgress>
)