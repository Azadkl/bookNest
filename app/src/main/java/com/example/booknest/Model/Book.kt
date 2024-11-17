package com.example.booknest.Model
 // This is the model package

// A simple Book class with title, author, and genre
data class Book(
    val title: String,
    val author: String? = null,  // Author is optional
    val genre: String? = null    // Genre is also optional
)
