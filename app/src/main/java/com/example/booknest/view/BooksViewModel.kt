package com.example.booknest.view

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.booknest.Model.Book
import com.example.booknest.Model.DummyData
import com.example.booknest.Model.SearchResult
import com.example.booknest.R

class BooksViewModel : ViewModel() {
    val books = mutableStateListOf(
        SearchResult.Book("101", "The Great Gatsby", "F. Scott Fitzgerald",
            R.drawable.houseoflame,"4.0", pageNumber = 300),
        SearchResult.Book("102", "1984", "George Orwell", R.drawable.farelerveinsanlar,"4.5",pageNumber = 300),
        SearchResult.Book("103","Fareler ve İnsanlar","John Steinbeck",
            R.drawable.farelerveinsanlar,"4.6",pageNumber = 300),
        SearchResult.Book("104","Shadows of Self","Brandon Sanderson", R.drawable.images,"3.6",pageNumber = 300),
        SearchResult.Book("105", "House of Flame and Shadow", "Sarah J. Maas",
            R.drawable.houseoflame,"5.0",pageNumber = 300),
        SearchResult.Book("106", "1984", "George Orwell", R.drawable.farelerveinsanlar,"4.5",pageNumber = 300),
        SearchResult.Book("107","Fareler ve İnsanlar","John Steinbeck",
            R.drawable.farelerveinsanlar,"4.6",pageNumber = 300),
        SearchResult.Book("108","Shadows of Self","Brandon Sanderson", R.drawable.images,"3.6",pageNumber = 300),
        SearchResult.Book("109", "The Great Gatsby", "F. Scott Fitzgerald",
            R.drawable.farelerveinsanlar,"4.0",pageNumber = 300),
    )

    // Add a book to the list


    // Remove a book from the list
    fun removeBook(book: SearchResult.Book) {
        books.remove(book)
    }
}

