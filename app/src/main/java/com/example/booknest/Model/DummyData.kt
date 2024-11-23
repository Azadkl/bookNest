package com.example.booknest.Model

import com.example.booknest.R

class DummyData {
    val dummyData = listOf(
        SearchResult.User("1", "Azad Köl", R.drawable.azad),
        SearchResult.User("2", "Muhammed Curri",R.drawable.azad),
        SearchResult.User("3", "Emircan Kocatepe",R.drawable.azad),
        SearchResult.Book("101", "The Great Gatsby", "F. Scott Fitzgerald",R.drawable.farelerveinsanlar,"4.0"),
        SearchResult.Book("102", "1984", "George Orwell",R.drawable.farelerveinsanlar,"4.5"),
        SearchResult.Book("103","Fareler ve İnsanlar","John Steinbeck",R.drawable.farelerveinsanlar,"4.6"),
        SearchResult.Book("104","Shadows of Self","Brandon Sanderson",R.drawable.images,"4.6")
    )

}