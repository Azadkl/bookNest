package com.example.booknest.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.booknest.Book
import com.example.booknest.R
import com.example.booknest.ui.theme.ButtonColor1

@Composable
fun MyBooksPage(modifier: Modifier=Modifier) {
    var search by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val books = listOf(
        Book(
            title = "Deneme",
            author = "Deneme",
            image = ""
        ),
        Book(
            title = "Deneme",
            author = "Deneme Deneme",
            image = ""
        ),
        Book(
            title = "Deneme",
            author = "Deneme",
            image = ""
        )
    )
    val wantToReadBooks = listOf(
        Book(
            title = "Deneme",
            author = "Deneme",
            image = ""
        ),
        Book(
            title = "Deneme",
            author = "Deneme",
            image = ""
        ),
        Book(
            title = "Deneme",
            author = "Deneme",
            image = ""
        ),
    )

    val currentlyReadingBook = Book(
        title = "Deneme",
        author = "Deneme",
        image = ""
    )



    Column (modifier= Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        ){
        OutlinedTextField(
            value = search,
            placeholder = { Text("Search my books") },
            onValueChange = { search = it },
            modifier = Modifier
                .padding(top = 10.dp, bottom = 25.dp)
                .size(width = 355.dp, height = 55.dp)
                .border(width = 0.dp, color = Color.Transparent)
                .focusable(),
            shape = RoundedCornerShape(15.dp),

            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),

        )
        Row (modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically){
            Text(text = "Books I've Read",style = MaterialTheme.typography.headlineSmall)
            IconButton(onClick = {},
                modifier=Modifier.size(35.dp)) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Edit Icon",
                    modifier = Modifier.size(25.dp),
                    tint = Color.Black
                )
            }
        }

        BookList(books = books, modifier = Modifier.size(255.dp, 155.dp))

        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Books I Want to Read", style = MaterialTheme.typography.headlineSmall)
            IconButton(onClick = {},
                modifier=Modifier.size(35.dp)) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Edit Icon",
                    modifier = Modifier.size(25.dp),
                    tint = Color.Black
                )
            }
        }
        BookList(books = wantToReadBooks, modifier = Modifier.size(255.dp, 155.dp))

        Spacer(modifier = Modifier.height(16.dp))
        Row (modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically){
            Text(text = "Currently Reading", style = MaterialTheme.typography.headlineSmall)
            IconButton(onClick = {},
                modifier=Modifier.size(35.dp)) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Edit Icon",
                    modifier = Modifier.size(25.dp),
                    tint = Color.Black
                )
            }
        }
        BookCard(book = currentlyReadingBook, modifier = Modifier.size(415.dp, 205.dp))
    }
}

@Composable
fun BookCard(modifier: Modifier=Modifier,book: Book){
        Card(
            modifier = modifier
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,) {
                Image(
                    painter = painterResource(R.drawable.loginimage),
                    contentDescription = "Book Cover Image",

                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = book.title,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "${book.author}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                }
            }
        }
}
@Composable
fun BookList(modifier: Modifier=Modifier,books: List<Book>){
    LazyRow (modifier = Modifier){
        items(books){
            book-> BookCard(book = book,modifier = Modifier.size(255.dp, 155.dp))
        }
    }
}

