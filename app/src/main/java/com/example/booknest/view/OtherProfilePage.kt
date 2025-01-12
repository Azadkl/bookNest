package com.example.booknest.view

import android.net.Uri
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.booknest.Model.SearchResult
import com.example.booknest.R // Ensure this imports your drawable resources
import com.example.booknest.ViewModel.LoginViewModel

    @Composable
    fun OtherProfilePage(
        id: Int,
        userName: String,
        userImageResId: String,
        navController: NavController,
        currentUser: String,
        viewModel: LoginViewModel
    ) {
        val otherProfileResponse = viewModel.otherResponse
        Log.d("id other","$id")
        // Arkadaşlık durumu: "None", "Pending", "Friend"
        val otherProfile = otherProfileResponse.value
        val friendshipStatus by remember(otherProfile) {
            mutableStateOf(
                when {
                    otherProfile?.isFriend == true -> "Friend"
                    otherProfile?.pendingSentRequest == true -> "Pending"
                    otherProfile?.pendingReceivedRequest == true -> "PendingReceived"
                    else -> "None"
                }
            )
        }


        var fetchOneResponse = viewModel.clickedBook
        Log.d("otherRepsone","${otherProfileResponse}")
        LaunchedEffect (Unit){
            viewModel.getOtherProfile(id)
        }


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
                            "Friend" -> {
                                otherProfile?.let {
                                    viewModel.removeFriend(it.id)
                                    // UI'yi hemen güncelle
                                    viewModel.updateFriendshipStatus(it.copy(isFriend = false))
                                }
                            }
                            "Pending" -> {
                                otherProfile?.let {
                                    viewModel.cancelFriendRequest(it.id)
                                    // UI'yi hemen güncelle
                                    viewModel.updateFriendshipStatus(it.copy(pendingSentRequest = false))
                                }
                            }
                            "PendingReceived" -> {
                                otherProfile?.let {
                                    viewModel.respondToFriendRequest(it.id, true)
                                    // UI'yi hemen güncelle
                                    viewModel.updateFriendshipStatus(it.copy(isFriend = true, pendingReceivedRequest = false))
                                }
                            }
                            "None" -> {
                                otherProfile?.let {
                                    viewModel.sendFriendRequest(it.id)
                                    // UI'yi hemen güncelle
                                    viewModel.updateFriendshipStatus(it.copy(pendingSentRequest = true))
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when (friendshipStatus) {
                            "Friend" -> Color.Green
                            "Pending", "PendingReceived" -> Color.Red
                            "None" -> Color.Blue
                            else -> Color.Gray
                        }
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = when (friendshipStatus) {
                            "Friend" -> "You are friends"
                            "Pending" -> "Cancel friend request"
                            "PendingReceived" -> "Accept request"
                            "None" -> "Add friend"
                            else -> "Unknown"
                        },
                        color = Color.White
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
                // Access the read books
                otherProfileResponse.value?.read?.let { readBooks ->
                Log.d("okunan raf","${otherProfileResponse.value?.read}")
                    // Display only the book covers
                    var isLoading by remember { mutableStateOf(false) }

                    LazyRow(
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(readBooks) { bookProgress ->
                            Image(
                                painter = rememberAsyncImagePainter(bookProgress.cover),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        isLoading = true
                                        viewModel.fetchOneBook(bookProgress.bookId) // Fetch the book
                                    },
                                contentScale = ContentScale.Crop
                            )

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

                }
            }



            // Want to Read Section
                    //BookSection(title = "Want to Read", isRecentlyRead = false, books = books, navController = navController)


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
