package com.example.booknest.view

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.substring
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.booknest.ViewModel.LoginViewModel
import com.example.booknest.api.Models.Book
import com.example.booknest.api.Models.BookProgress

@Composable
fun ToRead(viewModel: LoginViewModel,navController: NavController) {
    val books = viewModel.myBooks.value?.wantToRead ?: emptyList()
    val newbook = viewModel.bookResponse
    var refreshBooks by remember { mutableStateOf(false) }
    LaunchedEffect(refreshBooks) {
        viewModel.getBookProgress()
    }

    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedBook by remember { mutableStateOf<Book?>(null) }
    var readingStatuses by remember { mutableStateOf<List<Pair<BookProgress, Int>>>(emptyList()) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Want to Read",
            style = TextStyle(fontSize = 40.sp),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            contentPadding = PaddingValues(bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(books) { book ->
                var pagesRead = 0
                var secilenBook: Book? = null

                val eleman = viewModel.bookResponse.value?.find { it.isbn == book.bookId }
                eleman?.let {
                    secilenBook = it
                }

                val status = readingStatuses.find { it.first.bookId == book.bookId }
                status?.let {
                    pagesRead = it.second
                }
                var isLoading by remember { mutableStateOf(false) }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            isLoading = true
                            viewModel.fetchOneBook(book.bookId) // Fetch the book
                        },

                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(book.cover ?: ""),
                            contentDescription = book.title,
                            modifier = Modifier.size(100.dp)
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = book.title,
                                style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            )
                            eleman?.author?.let {
                                Text(
                                    text = "by $it",
                                    style = TextStyle(fontSize = 18.sp, color = Color.Gray)
                                )
                            }
                            eleman?.rating?.let {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    RatingStars(rating = it)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "$it".substring(0,3),
                                        style = TextStyle(fontSize = 16.sp, color = Color.Gray)
                                    )
                                }

                            }
                            LinearProgressIndicator(
                                progress = book.progress / 100f,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = Color(0xFF3CB371),
                                trackColor = Color(0xFFD3D3D3)
                            )
                            Text(text = "Completion: ${book.progress}%", fontSize = 14.sp)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Button(
                                    onClick = {
                                        secilenBook?.isbn?.let { isbn ->
                                            viewModel.deleteBookProgress(isbn = isbn)
                                        }
                                    },
                                    modifier = Modifier.padding(top = 8.dp),
                                    colors = ButtonDefaults.buttonColors(Color(0xFF2E8B57))
                                ) {
                                    Text(text = "Remove")
                                }

                                Button(
                                    onClick = {
                                        selectedBook = secilenBook
                                        viewModel.postBookProgress(bookId = selectedBook!!.isbn, status = "Reading", progress =  0)
                                        showBottomSheet = true
                                    },
                                    modifier = Modifier.padding(top = 8.dp),
                                    colors = ButtonDefaults.buttonColors(Color(0xFF2E8B57))
                                ) {
                                    Text(text = "Start",)
                                }
                            }
                        }
                    }
                }
                if (isLoading) {
                    LaunchedEffect(viewModel.clickedBook.value) {
                        val book = viewModel.clickedBook.value
                        if (book != null) {
                            val safeAuthor = book.author ?: "Unknown"
                            Log.d("Navigation URI", "books/${Uri.encode(book.isbn)}/${Uri.encode(book.title)}/${Uri.encode(safeAuthor)}")
                            navController.navigate(
                                "books/${Uri.encode(book.isbn)}/${Uri.encode(book.title)}/${Uri.encode(safeAuthor)}/${Uri.encode(book.cover)}/${Uri.encode(book.description)}/${book.rating}/${book.pages}/${Uri.encode(book.category)}/${Uri.encode(book.language)}/${Uri.encode(book.publishedDate)}/${Uri.encode(book.publisher)}"
                            ) {
                                popUpTo(navController.currentDestination?.id ?: return@navigate) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                            viewModel.resetClickedBook()
                        }
                    }
                }
            }
        }
    }}

