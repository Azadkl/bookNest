package com.example.booknest.view

import androidx.compose.material3.Typography
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
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import coil.compose.rememberAsyncImagePainter
import com.example.booknest.Book
import com.example.booknest.R
import com.example.booknest.ViewModel.LoginViewModel
import com.example.booknest.api.Models.NotificationResponse
import com.example.booknest.api.Models.ReceivedRequest
import com.example.booknest.ui.theme.ButtonColor1
import com.example.booknest.ui.theme.PrimaryColor


@Composable
fun HomePageScreen(modifier: Modifier = Modifier, viewModel: LoginViewModel) {
    var search by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.getNotification()
    }
    var notifications = viewModel.notifications
    Column(
        modifier = Modifier
            .fillMaxHeight()
            //.fillMaxWidth()
            .padding( top = 100.dp, end = 16.dp, start = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "what's happening around?",
                        modifier = Modifier.padding(bottom = 10.dp),
                        style = TextStyle(fontSize = 25.sp)
                    )
                    Divider(
                        color = Color.Black,
                        thickness = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    viewModel.notifications.value?.let { NotificationContent(notificationList = it,viewModel) }
                }

    }
}
@Composable
fun NotificationContent(notificationList: List<NotificationResponse>, viewModel: LoginViewModel) {
    val filteredNotifications = notificationList?.filter { notification ->
        notification.type=="bookprogress"
    } ?: emptyList()
    LazyColumn(modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        items(filteredNotifications) { notification ->
            StatusCard( notification,viewModel )
        }
    }
}
@Composable
fun StatusCard(notification: NotificationResponse,viewModel: LoginViewModel) {
    var dropdownExpanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp)
            .padding(vertical = 8.dp)
            .border(1.dp, Color.Gray),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            if (notification.status=="reading") {
                Text(
                    text = "${notification.userName} has read ${notification.progress}% of ${notification.bookTitle}",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            else if (notification.status=="read") {
                Text(
                    text = "${notification.userName} has read ${notification.bookTitle}",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            else if(notification.status=="wanttoread"){
                Text(
                    text = "${notification.userName} wants to read ${notification.bookTitle}",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            else{
                Text(
                    text = "",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(notification.bookCover),
                    contentDescription = "Book Cover Image",
                    modifier = Modifier
                        .size(80.dp)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "${notification.bookTitle}",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "${notification.author}",
                        modifier = Modifier.fillMaxWidth()
                    )


                    }
                }
            }
        }
    }


