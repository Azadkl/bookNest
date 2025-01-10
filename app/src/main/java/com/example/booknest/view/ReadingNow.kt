package com.example.booknest.view

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.booknest.Model.SearchResult
import com.example.booknest.ViewModel.LoginViewModel
import com.example.booknest.api.Models.Book
import com.example.booknest.api.Models.BookProgress
import com.example.booknest.api.Models.PostBook


@Composable
fun ReadingNow(viewModel: LoginViewModel, navController: NavController) {
    val books = viewModel.myBooks.value?.reading ?: emptyList()
    val newbook = viewModel.bookResponse

    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedBook by remember { mutableStateOf<Book?>(null) }
    var readingStatuses by remember { mutableStateOf<List<Pair<BookProgress, Int>>>(emptyList()) }
    LaunchedEffect(Unit) {
        viewModel.fetchBook()
        viewModel.getBookProgress()
    }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Reading Now",
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

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp)),

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
                                Row(modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = "Completion: ${book.progress}%", fontSize = 14.sp)
                                    eleman?.pages?.let {
                                        Text(
                                            text = "Pages: $it",
                                            style = TextStyle(fontSize = 14.sp)
                                        )
                                    }
                                }


                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Button(
                                        onClick = {
                                           viewModel.deleteBookProgress(postBook = PostBook(isbn = secilenBook!!.isbn))
                                        },
                                        modifier = Modifier.padding(top = 8.dp),
                                        colors = ButtonDefaults.buttonColors(Color(0xFF2E8B57))
                                    ) {
                                        Text(text = "Remove")
                                    }

                                    Button(
                                        onClick = {
                                            selectedBook = secilenBook
                                            showBottomSheet = true
                                        },
                                        modifier = Modifier.padding(top = 8.dp),
                                        colors = ButtonDefaults.buttonColors(Color(0xFF2E8B57))
                                    ) {
                                        Text(text = "Update")
                                    }
                                }
                            }
                        }
                    }
                }

            }

        }






    Log.d("update state","$selectedBook")
        if (showBottomSheet && selectedBook != null) {
            BottomSheet_2(
                selectedBook = selectedBook!!,
                onDismiss = { showBottomSheet = false },
                onSave = { pagesRead ->

                    // Eğer %100'e ulaşmışsa, kitabı listeden çıkaralım
                    if (pagesRead == selectedBook!!.pages) {
                        viewModel.postBookProgress(selectedBook!!.isbn,"read",100)
                    }
                    else{
                        viewModel.postBookProgress(selectedBook!!.isbn,"reading",(pagesRead*100/selectedBook!!.pages))

                    }


                    showBottomSheet = false
                }
            )
        }
    }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet_2(
    selectedBook: Book,
    onDismiss: () -> Unit,
    onSave: (Int) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var pagesRead by remember { mutableStateOf("") }

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(0.5f),
        sheetState = sheetState,
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Update Status for ${selectedBook.title}",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )
            OutlinedTextField(
                value = pagesRead,
                onValueChange = { pagesRead = it },
                label = { Text("Pages Read") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(16.dp)
            )
            Button(
                onClick = {
                    val pages = pagesRead.toIntOrNull() ?: 0 // Geçersiz sayfa girişi olduğunda 0 yap
                    onSave(pages) // Sayfa okunduğunda status kaydedilir
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E8B57)),
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Save")
            }
        }
    }
}

