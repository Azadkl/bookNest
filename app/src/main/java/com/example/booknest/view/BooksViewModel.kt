package com.example.booknest.view

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.booknest.Model.Book
import com.example.booknest.Model.DummyData
import com.example.booknest.Model.SearchResult

class BooksViewModel : ViewModel() {
    val books = mutableStateListOf(
        Book("To Kill a Mockingbird", "Harper Lee", "Fiction"),
        Book("1984", "George Orwell", "Dystopian"),
        Book("Pride and Prejudice", "Jane Austen", "Romance"),
        Book("The Great Gatsby", "F. Scott Fitzgerald", "Classic"),
        Book("Moby Dick", "Herman Melville", "Adventure")
    )

    // Add a book to the list


    // Remove a book from the list
    fun removeBook(book: Book) {
        books.remove(book)
    }
}

