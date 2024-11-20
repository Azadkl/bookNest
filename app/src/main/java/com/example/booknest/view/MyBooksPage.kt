package com.example.booknest.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.booknest.Book
import com.example.booknest.R
import com.example.booknest.ui.theme.ButtonColor1
import com.example.booknest.ui.theme.PrimaryColor
import com.google.android.mediahome.books.BookItem

@Composable
fun MyBooksPage(navController: NavController, modifier: Modifier = Modifier) {
    var search by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),

        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = search,
            placeholder = { Text("Search my books") },
            onValueChange = { search = it },
            modifier = Modifier
                .padding(vertical = 20.dp)
                .size(width = 320.dp, height = 56.dp),
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
        )

        LazyColumn(

            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),

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
                    Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.fillMaxWidth(0.8f))                    BookCard(onClick = { navController.navigate("booksIveRead") })

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
                    Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.fillMaxWidth(0.8f))                    BookCard(onClick = { navController.navigate("booksIWantToRead") })

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
                    BookCard(onClick = { navController.navigate("currentlyReading") })

                }
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }

    }
}
@Composable
fun BookCard(onClick: () -> Unit) {

        Spacer(modifier = Modifier.padding(3.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(R.drawable.farelerveinsanlar),
                contentDescription = "Book Cover Image",
            )
        }
    Spacer(modifier = Modifier.padding(3.dp))
        Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
}
