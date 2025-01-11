package com.example.booknest.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.booknest.Book
import com.example.booknest.Model.SearchResult
import com.example.booknest.R
import com.example.booknest.ViewModel.LoginViewModel
import com.example.booknest.api.Models.BookProgress
import com.example.booknest.ui.theme.ButtonColor1
import com.example.booknest.ui.theme.PrimaryColor

@Composable
fun MyBooksPage(navController: NavController, modifier: Modifier = Modifier,viewModel: LoginViewModel) {

    val myBooks = viewModel.myBooks
    Log.d("mybooks icerigi","$myBooks")
    LaunchedEffect(Unit) {
        viewModel.getBookProgress()
        println("Fetched books: ${viewModel.myBooks.value}")
    }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),

        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        LazyColumn(

            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(top = 100.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Books I've Read",
                        modifier = Modifier.padding(bottom = 10.dp),
                        style = TextStyle(fontSize = 25.sp)

                    )
                    Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.fillMaxWidth(0.8f))
                    myBooks.value?.let {
                        BookCard( navController=navController,"booksIveRead",
                            it.read)
                    }

                }
                Spacer(modifier = Modifier.padding(8.dp))

                Spacer(modifier = Modifier.padding(8.dp))
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Books I Want To Read",
                        modifier = Modifier.padding(bottom = 10.dp),
                        style = TextStyle(fontSize = 25.sp)
                    )
                    Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.fillMaxWidth(0.8f))
                    myBooks.value?.let {
                        BookCard( navController=navController,"booksIWantToRead",
                            it.wantToRead)
                    }

                }
                Spacer(modifier = Modifier.padding(8.dp))

                Spacer(modifier = Modifier.padding(8.dp))
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Currently Reading",
                        modifier = Modifier.padding(bottom = 10.dp),
                        style = TextStyle(fontSize = 25.sp)
                    )
                    Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.fillMaxWidth(0.8f))
                    myBooks.value?.let {
                        BookCard( navController=navController,"currentlyReading",
                            it.reading)
                    }

                }
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }

    }
}
@Composable
fun BookCard(navController: NavController, route: String, books: List<BookProgress>) {
    Spacer(modifier = Modifier.padding(3.dp))
Row (modifier = Modifier.fillMaxWidth()){

    LazyRow(
        modifier = Modifier
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            books.forEachIndexed { index, book ->
                if (books.size >= 4) { // Only apply offset and zIndex when there are 4 or more books
                    Box(
                        modifier = Modifier
                            .offset(x = (-30 * (index - 2)).dp)
                            .zIndex(books.size - index.toFloat())
                    ) {
                        Image(
                            modifier = Modifier
                                .size(120.dp, 180.dp)
                                .clickable { navController.navigate(route) },
                            painter = rememberImagePainter(book.cover),
                            contentDescription = "Book Cover Image",
                            contentScale = ContentScale.Crop
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
                    ){
                        Image(
                            modifier = Modifier
                                .size(120.dp, 180.dp)
                                .clickable { navController.navigate(route) },
                            painter = rememberImagePainter(book.cover),
                            contentDescription = "Book Cover Image",
                            contentScale = ContentScale.Crop
                        )
                    }

                }
            }
        }

    }


}


    Spacer(modifier = Modifier.padding(3.dp))
    Divider(
        color = Color.Black,
        thickness = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}
