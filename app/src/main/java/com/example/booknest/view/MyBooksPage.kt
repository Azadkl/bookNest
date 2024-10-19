package com.example.booknest.view

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.booknest.Book
import com.example.booknest.R

@Composable
fun MyBooksPage(modifier: Modifier=Modifier) {
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
    )

    val currentlyReadingBook = Book(
        title = "Deneme",
        author = "Deneme",
        image = ""
    )



    Column (modifier= Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.Start,
        ){
        Text(text = "Books I've Read",style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(start = 15.dp))
        BookList(books = books)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Books I Want to Read", style = MaterialTheme.typography.headlineSmall,modifier = Modifier.padding(start = 15.dp))
        BookList(books = wantToReadBooks)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Currently Reading", style = MaterialTheme.typography.headlineSmall,modifier = Modifier.padding(start = 15.dp))
        BookCard(book = currentlyReadingBook)
    }
}

@Composable
fun BookCard(book: Book){
        Card(
            modifier = Modifier.fillMaxWidth().padding(8.dp).size(width = 255.dp, height = 155.dp),
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
fun BookList(books: List<Book>){
    LazyRow  {
        items(books){
            book-> BookCard(book = book)
        }
    }
}

