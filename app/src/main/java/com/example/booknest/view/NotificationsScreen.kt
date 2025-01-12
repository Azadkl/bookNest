package com.example.booknest.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.booknest.R
import com.example.booknest.ViewModel.LoginViewModel
import com.example.booknest.api.Models.NotificationResponse
import com.example.booknest.api.Models.ReceivedRequest

@Composable
fun NotificationsScreen(navController: NavController,viewModel: LoginViewModel) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("NOTIFICATIONS", "REQUEST")


    Column (modifier = Modifier.fillMaxSize()){

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
                text = "Notifications",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Medium, fontSize = 20.sp),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),

            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                )
            }
        ) {
            // Her bir Tab
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(text = title) }
                )
            }
        }


        when (selectedTabIndex) {
            0 -> viewModel.notifications.value?.let { NotificationsContent(notificationList = it) }
            1 -> RequestContent(receivedRequests = viewModel.friendRequestsReceived.value,viewModel)
        }
    }
}

@Composable
fun NotificationsContent(notificationList: List<NotificationResponse>) {
    val filteredNotifications = notificationList?.filter { notification ->
        notification.type=="friends"
    } ?: emptyList()

    LazyColumn(modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
items(filteredNotifications) {notification->

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profil Resmi
        Image(
            painter = rememberImagePainter(notification.userAvatar),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Gray, CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Kullanıcı Adı ve Zaman Bilgisi
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "${notification.userName} is now your friend", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "${getTimeAgo_2(notification.createdAt)}", style = MaterialTheme.typography.bodySmall, fontSize = 15.sp)
        }

        Spacer(modifier = Modifier.weight(1f))

    }
}
}

}


@Composable
fun RequestContent(receivedRequests: List<ReceivedRequest>,viewModel: LoginViewModel) {
    LazyColumn {
        items(receivedRequests) { request ->
            FriendRequestItem(request, viewModel )
        }
    }
}

@Composable
fun FriendRequestItem(request: ReceivedRequest,viewModel: LoginViewModel) {
    val timeAgo = getTimeAgo_2(request.createdAt)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(request.avatar),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Gray, CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Kullanıcı Adı ve Zaman Bilgisi
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = request.username, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = timeAgo, style = MaterialTheme.typography.bodySmall, fontSize = 15.sp)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Onay ve Red Butonları
        Row {
            IconButton(onClick = { viewModel.respondToFriendRequest(request.senderId, false) }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Reject")
            }
            IconButton(onClick = { viewModel.respondToFriendRequest(request.senderId, true) }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Accept")
            }
        }
    }
}





@Composable
fun NotificationItem(notification: Notification) {
    val timeAgo = getTimeAgo(notification.time)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profil Resmi
        Image(
            painter = rememberImagePainter(notification.profileImageUrl),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Gray, CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Kullanıcı Adı ve Bildirim Mesajı
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "${notification.userName}"+"${notification.message}", style = MaterialTheme.typography.bodyMedium, fontSize = 18.sp)
            Text(text = timeAgo, style = MaterialTheme.typography.bodySmall, fontSize = 15.sp)
        }
    }
}

data class Notification(
    val userName: String,
    val message: String,
    val time: Long, // Unix timestamp
    val profileImageUrl: Int // Resim kaynağı ID'si
)

fun getTimeAgo(time: Long): String {
    val currentTime = System.currentTimeMillis()
    val duration = currentTime - time

    val seconds = duration / 1000
    val minutes = duration / 60
    val hours = minutes / 60
    val days = hours / 24
    val weeks = days / 7
    val months = days / 30
    val years = days / 365

    return when {
        seconds < 60 -> "$seconds s ago"
        minutes < 60 -> "$minutes m ago"
        hours < 24 -> "$hours h ago"
        days < 7 -> "$days d ago"
        weeks < 4 -> "$weeks w ago"
        months < 12 -> "$months mo ago"
        else -> "$years y ago"
    }
}
