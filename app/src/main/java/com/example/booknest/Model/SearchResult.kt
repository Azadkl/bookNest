package com.example.booknest.Model

import android.media.Image
import android.media.Rating

sealed class SearchResult {
    data class User(val id: String, val name: String,val imageResId:Int) : SearchResult()
    data class Book(val id: String, val title: String, val author: String,val imageResId: Int,val rating: String,val pageNumber:Int) : SearchResult()
}
data class Group(
    val id: Int,
    val name: String,
    val imageResId: Int
)

data class Group_2(
    val name: String,
    val members: List<String>, // List of member names
    val currentBook: Book, // The book currently being read by the group
    val books: List<Book> // List of books associated with the group
)