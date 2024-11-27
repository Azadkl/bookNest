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
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.booknest.R

@Composable
fun NotificationsScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("NOTIFICATIONS", "REQUEST")
    val friendRequests = remember { mutableStateOf(listOf<FriendRequest>()) }
    // Friend requests backend'den çekiliyor (simülasyon)
    LaunchedEffect(Unit) {
        friendRequests.value = fetchFriendRequestsFromBackend()
    }
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
            0 -> NotificationsContent()
            1 -> RequestContent(friendRequests = friendRequests.value)
        }
    }
}

@Composable
fun NotificationsContent() {
    // Örnek bildirim verileri
    val notifications = remember {
        listOf(
            Notification("Azad", " is now your friend", System.currentTimeMillis() - 500000, R.drawable.azad),
            Notification("Azad", " read a new book: 'Kotlin Programming'", System.currentTimeMillis() - 3600000, R.drawable.azad),
            Notification("John", " is now your friend", System.currentTimeMillis() - 86400000, R.drawable.azad),
            Notification("Azad", " is now your friend", System.currentTimeMillis() - 86400000, R.drawable.azad),
            Notification("John", " read a new book: 'Advanced Kotlin'", System.currentTimeMillis() - 259200000, R.drawable.azad)
        )
    }

    // Bildirimleri zaman dilimlerine göre grupla
    val todayNotifications = notifications.filter { it.time > System.currentTimeMillis() - 86400000 }
    val thisWeekNotifications = notifications.filter { it.time > System.currentTimeMillis() - 604800000 }
    val thisMonthNotifications = notifications.filter { it.time > System.currentTimeMillis() - 2592000000 }

    // LazyColumn ile tüm bildirimleri tek bir liste içinde grupla
    LazyColumn(modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Today Notifications
        if (todayNotifications.isNotEmpty()) {
            item {
                Text(text = "Today", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(16.dp))
            }
            items(todayNotifications) { notification ->
                NotificationItem(notification)
            }
        }

        // This Week Notifications
        if (thisWeekNotifications.isNotEmpty()) {
            item {
                Text(text = "This Week", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(16.dp))
            }
            items(thisWeekNotifications) { notification ->
                NotificationItem(notification)
            }
        }

        // This Month Notifications
        if (thisMonthNotifications.isNotEmpty()) {
            item {
                Text(text = "This Month", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(16.dp))
            }
            items(thisMonthNotifications) { notification ->
                NotificationItem(notification)
            }
        }
    }
}



data class FriendRequest(
    val userId: String,
    val userName: String,
    val userProfileImageUrl: Int,
    val requestTime: Long // Unix timestamp (milisaniye cinsinden)
)

@Composable
fun RequestContent(friendRequests: List<FriendRequest>) {
    LazyColumn {
        items(friendRequests) { request ->
            FriendRequestItem(request)
        }
    }
}

@Composable
fun FriendRequestItem(request: FriendRequest) {
    val timeAgo = getTimeAgo(request.requestTime)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profil Resmi
        Image(
            painter = rememberImagePainter(request.userProfileImageUrl),
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
            Text(text = request.userName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = timeAgo, style = MaterialTheme.typography.bodySmall, fontSize = 15.sp)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Onay ve Red Butonları
        Row {
            IconButton(onClick = { /* Red action */ }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Reject")
            }
            IconButton(onClick = { /* Accept action */ }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Accept")
            }
        }
    }
}
@Composable
fun FriendRequestsScreen() {
    val friendRequests = remember { mutableStateOf(listOf<FriendRequest>()) }

    LaunchedEffect(Unit) {
        // Burada backend'den veri çekme işlemini yapıyoruz (örneğin Retrofit kullanabilirsiniz)
        friendRequests.value = fetchFriendRequestsFromBackend()
    }

    RequestContent(friendRequests = friendRequests.value)
}

suspend fun fetchFriendRequestsFromBackend(): List<FriendRequest> {
    // Backend API çağrısı (Retrofit, Ktor vb.)
    // Bu sadece bir örnektir, kendi backend implementasyonunuzu kullanın
    return listOf(
        FriendRequest("1", "John Doe",  R.drawable.azad,System.currentTimeMillis() - 3600000),
        FriendRequest("2", "Jane Smith", R.drawable.azad, System.currentTimeMillis() - 7200000)
    )
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
    val minutes = duration / (1000 * 60)
    val hours = duration / (1000 * 60 * 60)
    val days = duration / (1000 * 60 * 60 * 24)

    return when {
        seconds < 60 -> "$seconds s ago"
        minutes < 60 -> "$minutes m ago"
        hours < 24 -> "$hours h ago"
        days < 7 -> "$days days ago"
        else -> "$days days ago"
    }
}