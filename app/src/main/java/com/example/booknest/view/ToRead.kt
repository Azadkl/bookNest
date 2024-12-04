package com.example.booknest.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ToRead(viewModel: BooksViewModel,navController: NavController) {
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
                text = "Books I Want To Read",
                style = TextStyle(fontSize = 30.sp),
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
                        .padding(8.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                        .clickable {
                            navController.navigate(
                                "books/${book.id}/${book.title}/${book.author}/${book.imageResId}/${book.rating}/${book.pageNumber}"
                            ) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                    // Rounded corners for the Card
                    // Use dp for CardElevation
                ) {
                    Row (modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically){
                        Image(
                            painter = painterResource(id = book.imageResId),
                            contentDescription = book.title,
                            modifier = Modifier.size(100.dp)
                        )
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
                            Row(modifier = Modifier.fillMaxWidth()) {
                                RatingStars(rating = book.rating.toFloatOrNull() ?: 0f)
                                Spacer(modifier = Modifier.width(8.dp))
                                book.rating?.let {
                                    Text(
                                        text = "$it",  // Genre of the book
                                        style = TextStyle(fontSize = 16.sp, color = Color.Gray)
                                    )
                                }
                            }


                            // Remove Button for each book
                            Button(
                                onClick = {
                                    viewModel.removeBook(book)
                                },
                                modifier = Modifier.padding(top = 8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    Color(0xFF2E8B57)
                                )
                            ) {
                                Text(text = "Remove")
                            }
                        }
                    }

                }

            }
        }
    }
}
