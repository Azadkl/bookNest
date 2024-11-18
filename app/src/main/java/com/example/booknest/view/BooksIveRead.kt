package com.example.booknest.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booknest.Model.Book
import androidx.compose.material3.Button
import androidx.compose.material3.Text


@Composable
fun BooksIveRead(viewModel: BooksViewModel) {
    val books = viewModel.books

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Header text

        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Books I've Read",
                style = TextStyle(fontSize = 40.sp),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // LazyColumn to display books
        LazyColumn(
            contentPadding = PaddingValues(bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Space between items
        ) {
            items(books) { book ->
                // Card for each book with title, author, and genre
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(16.dp),  // Rounded corners for the Card
                   // Use dp for CardElevation
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)  // Inner padding for content
                    ) {
                        // Book title, author, and genre
                        Text(
                            text = book.title,  // Book title
                            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        )

                        book.author?.let {
                            Text(
                                text = "by $it",  // Author name (if available)
                                style = TextStyle(fontSize = 18.sp, color = Color.Gray)
                            )
                        }

                        book.genre?.let {
                            Text(
                                text = "Genre: $it",  // Genre of the book
                                style = TextStyle(fontSize = 16.sp, color = Color.Gray)
                            )
                        }

                        // Remove Button for each book
                        Button(
                            onClick = {
                                viewModel.removeBook(book)
                            },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text(text = "Remove")
                        }
                    }
                }

            }
        }
    }
}
