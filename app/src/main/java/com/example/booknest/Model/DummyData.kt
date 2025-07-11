package com.example.booknest.Model

import com.example.booknest.R

class DummyData {
    val dummyData = listOf(
        SearchResult.User("1", "Azad Köl", R.drawable.azad),
        SearchResult.User("2", "Muhammed Curri",R.drawable.azad),
        SearchResult.User("3", "Emircan Kocatepe",R.drawable.azad),
        SearchResult.Book("101", "The Great Gatsby", "F. Scott Fitzgerald", R.drawable.houseoflame, "4.0", pageNumber = 300),
        SearchResult.Book("102", "1984", "George Orwell", R.drawable.farelerveinsanlar, "4.5", pageNumber = 300),
        SearchResult.Book("103", "Fareler ve İnsanlar", "John Steinbeck", R.drawable.farelerveinsanlar, "4.6", pageNumber = 300),
        SearchResult.Book("104", "Shadows of Self", "Brandon Sanderson", R.drawable.images, "3.6", pageNumber = 300),
        SearchResult.Book("105", "House of Flame and Shadow", "Sarah J. Maas", R.drawable.houseoflame, "5.0", pageNumber = 300),
        SearchResult.Book("106", "1984", "George Orwell", R.drawable.farelerveinsanlar, "4.5", pageNumber = 300),
        SearchResult.Book("107", "Fareler ve İnsanlar", "John Steinbeck", R.drawable.farelerveinsanlar, "4.6", pageNumber = 300),
        SearchResult.Book("108", "Shadows of Self", "Brandon Sanderson", R.drawable.images, "3.6", pageNumber = 300),
        SearchResult.Book("109", "The Great Gatsby", "F. Scott Fitzgerald", R.drawable.farelerveinsanlar, "4.0", pageNumber = 300),
        SearchResult.Book("110", "House of Flame and Shadow", "Sarah J. Maas", R.drawable.houseoflame, "5.0", pageNumber = 300),
        SearchResult.Book("111", "Fareler ve İnsanlar", "John Steinbeck", R.drawable.farelerveinsanlar, "4.6", pageNumber = 300),
        SearchResult.Book("112", "Shadows of Self", "Brandon Sanderson", R.drawable.images, "3.6", pageNumber = 300),
        SearchResult.Book("113", "The Great Gatsby", "F. Scott Fitzgerald", R.drawable.farelerveinsanlar, "4.0", pageNumber = 300),
        SearchResult.Book("114", "1984", "George Orwell", R.drawable.farelerveinsanlar, "4.5", pageNumber = 300),
        SearchResult.Book("115", "House of Flame and Shadow", "Sarah J. Maas", R.drawable.houseoflame, "5.0", pageNumber = 300),
        SearchResult.Book("116", "Shadows of Self", "Brandon Sanderson", R.drawable.images, "3.6", pageNumber = 300)
    )
    val booksOnly: List<SearchResult.Book> = dummyData.filterIsInstance<SearchResult.Book>()

}

class DummyDataGroups {
    val dummyGroups = listOf(
        Group(id = 1, name = "Book Lovers", imageResId = R.drawable.sharp_groups_24),
        Group(id = 2, name = "Sci-Fi Readers", imageResId = R.drawable.sharp_groups_24),
        Group(id = 3, name = "Mystery Novels", imageResId = R.drawable.sharp_groups_24),
        Group(id = 4, name = "Fantasy Adventures", imageResId = R.drawable.sharp_groups_24),
        Group(id = 5, name = "Fantasy Adventures", imageResId = R.drawable.sharp_groups_24),

    )
}
