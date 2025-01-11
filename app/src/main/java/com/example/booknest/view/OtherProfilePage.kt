package com.example.booknest.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import com.example.booknest.Model.SearchResult
import com.example.booknest.R // Ensure this imports your drawable resources
import com.example.booknest.ViewModel.LoginViewModel

    @Composable
    fun OtherProfilePage(
        userName: String,
        userImageResId: String,
        navController: NavController,
        currentUser: String,
        viewModel: LoginViewModel
    ) {
        // Arkadaşlık durumu: "None", "Pending", "Friend"
        var friendshipStatus by remember { mutableStateOf("None") }


        // Dummy list of books
        val books = listOf(
            SearchResult.Book("101", "The Great Gatsby", "F. Scott Fitzgerald", R.drawable.farelerveinsanlar, "4.0",pageNumber = 300),
            SearchResult.Book("102", "1984", "George Orwell", R.drawable.farelerveinsanlar, "4.5",pageNumber = 300),
            SearchResult.Book("103", "Fareler ve İnsanlar", "John Steinbeck", R.drawable.farelerveinsanlar, "4.6",pageNumber = 300),
            SearchResult.Book("104", "Shadows of Self", "Brandon Sanderson", R.drawable.images, "3.6",pageNumber = 300),
            SearchResult.Book("105", "House of Flame and Shadow", "Sarah J. Maas", R.drawable.houseoflame, "5.0",pageNumber = 300),
            SearchResult.Book("106", "1984", "George Orwell", R.drawable.farelerveinsanlar, "4.5",pageNumber = 300)
        )

        // Main Column for layout
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F8)) // Light background
                .verticalScroll(rememberScrollState()) // Scrollable content
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Header and Stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileHeader(userName = userName, userImageResId = userImageResId)

                Spacer(modifier = Modifier.width(8.dp))

                StatsRow()
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Friendship and Follow Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Friend Request Button
                // Friendship Button
                Button(
                    onClick = {
                        when (friendshipStatus) {
                            "None" -> friendshipStatus = "Pending" // İstek gönderildi
                            "Pending" -> friendshipStatus = "Friend" // İstek kabul edildi
                            "Friend" -> friendshipStatus = "None" // Arkadaşlıktan çıkarıldı
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when (friendshipStatus) {
                            "None" -> Color.Blue
                            "Pending" -> Color.Red
                            "Friend" -> Color.Green
                            else -> Color.Gray
                        }
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = when (friendshipStatus) {
                            "None" -> "Add Friend"
                            "Pending" -> "Pending"
                            "Friend" -> "Your Friend"
                            else -> "Unknown"
                        },
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }





            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(74.dp)
                            .clip(shape = CircleShape)
                            .clickable { /* Implement challenge logic */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.target),
                            contentDescription = "Challenge Icon",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(75.dp)
                        )

                    }
                    Text(text = "Start Challange")

                }
                Column(modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(74.dp)
                            .clip(shape = CircleShape)
                            .clickable { /* Implement challenge logic */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.awards),
                            contentDescription = "Challenge Icon",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(64.dp)
                        )

                    }
                    Text(text = "Awards")

                }
            }





            // Books Section
            Text(
                text = "BOOKS",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Recently Read and Want to Read Sections
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Pass the books list to the BookSection composable
                BookSection(title = "Recently Read", isRecentlyRead = true, books = books, navController = navController)

            }


            // Want to Read Section
            BookSection(title = "Want to Read", isRecentlyRead = false, books = books, navController = navController)


        }
    }

    @Composable
    fun BookSection(
        title: String,
        isRecentlyRead: Boolean,
        books: List<SearchResult.Book>,
        navController: NavController
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
        ) {
            // Title for the section (e.g., "Recently Read", "Want to Read")
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Displaying books in a horizontal row (LazyRow)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(books) { book ->
                    // Display each book item as an image with a clickable action
                    Image(
                        painter = painterResource(id = book.imageResId),
                        contentDescription = book.title,
                        modifier = Modifier
                            .width(100.dp)
                            .height(150.dp)
                            .padding(end = 10.dp)
                            .clickable {
                                navController.navigate(
                                    "books/${book.id}/${book.title}/${book.author}/${book.imageResId}/${book.rating}/${book.pageNumber}"
                                )
                            },
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
