package com.example.booknest.Model

import android.media.Image
import android.media.Rating

sealed class SearchResult {
    data class User(val id: String, val name: String,val imageResId:Int) : SearchResult()
    data class Book(val id: String, val title: String, val author: String,val imageResId: Int,val rating: String) : SearchResult()
}
data class Group(
    val id: Int,
    val name: String,
    val imageResId: Int
)

