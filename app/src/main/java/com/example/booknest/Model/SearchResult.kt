package com.example.booknest.Model

sealed class SearchResult {
    data class User(val id: String, val name: String) : SearchResult()
    data class Book(val id: String, val title: String, val author: String) : SearchResult()
}
