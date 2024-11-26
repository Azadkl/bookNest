package com.example.booknest.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booknest.Model.DummyData
import com.example.booknest.Model.SearchResult
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentScreen(navController: NavController) {
    val dummyData = DummyData().dummyData.filterIsInstance<SearchResult.User>()
    var comments by remember {
        mutableStateOf(
            listOf(
                Comment(dummyData[0], "Great book!", Date()),
                Comment(dummyData[1], "Loved it!", Date())
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier.size(35.dp)
            )
        }
        Text(
            text = "Comments",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Medium, fontSize = 20.sp),
            modifier = Modifier.align(Alignment.Center)
        )
    }

    CommentBox(
        comments = comments,
        onAddComment = { newComment ->
            comments = comments + newComment
        },
        users = dummyData,

    )
}

@Composable
fun CommentBox(
    comments: List<Comment>,
    onAddComment: (Comment) -> Unit,
    users: List<SearchResult.User>,
    modifier: Modifier = Modifier
) {
    var newComment by remember { mutableStateOf("") }
    val currentUser = users.firstOrNull()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(comments) { comment ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 18.dp),


                ) {
                    Image(
                        painter = painterResource(id = comment.user.imageResId),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(48.dp).clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = comment.user.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = comment.text,
                            fontSize = 16.sp,
                            color = Color.Gray
                        )

                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = formatDate(comment.timestamp),
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        textAlign = TextAlign.End
                    )
                }
                Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 100.dp, start = 15.dp, end = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newComment,
                onValueChange = { newComment = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text(text = "Write a comment...") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (newComment.isNotBlank() && currentUser != null) {
                        onAddComment(
                            Comment(
                                user = currentUser,
                                text = newComment.trim(),
                                timestamp = Date()
                            )
                        )
                        newComment = ""
                    }
                }
            ) {
                Text(text = "Send")
            }
        }
    }
}

fun formatDate(date: Date): String {
    val formatter = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
    return formatter.format(date)
}

data class Comment(
    val user: SearchResult.User,
    val text: String,
    val timestamp: Date
)
