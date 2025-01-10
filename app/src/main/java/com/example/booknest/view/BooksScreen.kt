package com.example.booknest.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.substring
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.palette.graphics.Palette
import coil.compose.rememberImagePainter
import com.example.booknest.Model.SearchResult
import com.example.booknest.R
import com.example.booknest.ViewModel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

@Composable
fun BooksScreen(navController: NavController,viewModel: LoginViewModel,
                isbn: String,
                title: String,
                author: String,
                cover: String,
                description:String,
                rating: String,
                pages: Int,
                publishedDate: String,
                publisher: String,
                language: String,
                category: String
) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var dominantColor by remember { mutableStateOf(Color.LightGray) }
    var vibrantColor by remember { mutableStateOf(Color.Gray) }
    val review by viewModel.reviews
    var refreshBooks by remember { mutableStateOf(false) }
    LaunchedEffect(refreshBooks) {

        viewModel.getReviewsByBook(bookId = isbn)
        viewModel.fetchBook()
    }

    // Ağ işlemini arka planda gerçekleştirin
    LaunchedEffect(cover) {
        withContext(Dispatchers.IO) {
            try {
                val inputStream = URL(cover).openStream()
                val originalBitmap = BitmapFactory.decodeStream(inputStream)
                val scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, 100, 100, true)

                // Palette işlemini de arka planda yapıyoruz
                val palette = Palette.Builder(scaledBitmap)
                    .maximumColorCount(24)
                    .generate()

                // UI güncellemelerini ana iş parçacığında yapın
                withContext(Dispatchers.Main) {
                    bitmap = scaledBitmap
                    dominantColor = palette.dominantSwatch?.rgb?.let { Color(it) } ?: Color.LightGray
                    vibrantColor = palette.vibrantSwatch?.rgb?.let { Color(it) } ?: dominantColor
                }
            } catch (e: Exception) {
                e.printStackTrace() // Hataları yakalayın
            }
        }
    }

    val animatedDominantColor by animateColorAsState(
        targetValue = dominantColor,
        animationSpec = tween(durationMillis = 1000)
    )

    val animatedVibrantColor by animateColorAsState(
        targetValue = vibrantColor,
        animationSpec = tween(durationMillis = 1000)
    )


    var userRating by remember { mutableFloatStateOf(0f) }
    var selectedStatus by remember { mutableStateOf("Want to Read") }
    var expanded by remember { mutableStateOf(false) }
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("ABOUT", "DETAILS","REVIEWS")
    var userComment by remember { mutableStateOf("") }

    // Durum seçenekleri
    val statusOptions = listOf("Want to Read", "Currently Reading", "I've Read")

    // Corresponding status options in the backend
    val statusOptionsBackEnd = listOf("wanttoread", "Reading", "read")

    // Create a map where the UI option is the key, and the backend option is the value
    val statusMapping = statusOptions.zip(statusOptionsBackEnd).toMap()

    // Function to get the backend status for a given UI status
    fun getBackEndStatus(uiStatus: String): String {
        return statusMapping[uiStatus] ?: "wanttoread"
    }




    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 190.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(animatedDominantColor, animatedVibrantColor),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
                    .graphicsLayer {
                        shadowElevation = 150.dp.toPx()
                    }
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(35.dp)
                    )
                }


                    Image(
                        painter = rememberImagePainter(cover),
                        contentDescription = "Book Cover",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(290.dp)
                    )

            }
        }
        item {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = title,
                style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.W500),
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "by ${author}",
                style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.W400),
            )
            Spacer(modifier = Modifier.height(35.dp))
            Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.fillMaxWidth(0.9f))
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val totalRatings = review.fold(0f) { total, review -> total + (review.rating) }
                Column(modifier = Modifier) {
                    val averageRating = if (review.isNotEmpty()) {
                        String.format("%.1f", totalRatings / review.size)
                    } else {
                        null
                    }

                    RatingStars((totalRatings / review.size))

                    Text(
                        text = averageRating ?: "No ratings yet",
                        style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Medium),
                    )
                }

                Column {
                    Text(
                        text = "$totalRatings ratings",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                    )
                    Text(
                        text = "${review.size} reviews", // Yorum sayısını dinamik olarak alıp gösterir
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.fillMaxWidth(0.9f))
            Spacer(modifier = Modifier.height(15.dp))
            Box {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column {
                        Button(
                            onClick = { expanded = true },
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(1.dp))
                                .padding(end = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2E8B57)
                            ),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(text = selectedStatus)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Select status"
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            statusOptions.forEach { status ->
                                Text(
                                    text = status,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedStatus = status
                                            expanded = false
                                        }
                                        .padding(8.dp)
                                )
                            }
                        }
                    }

                    Button(
                        onClick = {
                            Log.d("BookProgress", "bookId: $isbn, status: ${getBackEndStatus(selectedStatus)}, progress: ${if (getBackEndStatus(selectedStatus).compareTo("read") == 0) 100 else 0}")
                            viewModel.postBookProgress(bookId = isbn, status = getBackEndStatus(selectedStatus), progress = if (getBackEndStatus(selectedStatus).compareTo("read") == 0) 100 else 0)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2E8B57)
                        ),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(text = "Update")
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                fontSize = 20.sp,
                text = "Rate this book:",
                color = Color.Black
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(5) { index ->
                    val starRating = index + 1
                    Icon(
                        imageVector = when {
                            userRating >= starRating -> Icons.Filled.Star
                            userRating >= starRating - 0.5f -> Icons.Filled.StarHalf
                            else -> Icons.Filled.StarBorder
                        },
                        contentDescription = "Star $starRating",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { userRating = starRating.toFloat() }
                    )
                }
            }
            // Yıldızların altında slider ile değerlendirme yapma
            Spacer(modifier = Modifier.height(16.dp))
            Text("Set rating:", fontSize = 18.sp, color = Color.Gray)

            // Slider
            Slider(
                value = userRating,
                onValueChange = { newValue ->
                    // Yarı yıldızları 0.5 adım olarak yuvarla
                    userRating = (newValue * 2).toInt() / 2f // 0.5'lik adımlarla yuvarlama
                },
                valueRange = 0f..5f, // 0 ile 5 arasında bir değer aralığı
                modifier = Modifier.width(150.dp),
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFF2E8B57),
                    activeTrackColor = Color(0xFF2E8B57),

                )
            )

            // Yorum
            OutlinedTextField(
                value = userComment,
                onValueChange = { userComment = it },
                placeholder = { Text("Your Comment") },
                modifier = Modifier.width(350.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedBorderColor = Color.White
                ),
                maxLines = 3,
            )

                    Button(onClick = {
                        // Yorum gönderildiğinde, yeni yorum verisini ekleme
                        val newReview = com.example.booknest.api.Models.Review(
                            bookId = isbn,  // Buraya mevcut kullanıcı adı eklenebilir
                            rating = userRating,
                            text = userComment,
                            date = System.currentTimeMillis(),
                            cover=cover,
                            username = author
                        )


                        userRating = 0f
                        userComment = ""
                        viewModel.createReview(newReview)
                        refreshBooks = !refreshBooks
                    },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E8B57)),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text("Submit Review")
                    }

        }

        item {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Transparent,

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
                0 -> (AboutContent(description))
                1 -> (DetailContent(   title =title,
                    publishDate = publishedDate,
                    publisher = publisher, isbn,
                    language = language,
                    pages = pages,
                    category = category))
                2 -> (ReviewContent(review))
            }

        }
    }
}
@Composable
fun AboutContent(description: String) {
    // Card ile düzenlenmiş içerik
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "About the Book",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color(0xFF2E8B57)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                ),
                color = Color.Gray
            )
        }
    }
}

@Composable
fun DetailContent(
    title: String,
    publishDate: String,
    publisher: String,
    isbn: String,
    pages: Int,
    language: String,
    category: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround,

    ) {
        DetailRow("Title", title)
        DetailRow("Publish Date", publishDate)
        DetailRow("Publisher", publisher)
        DetailRow("ISBN", isbn)
        DetailRow("Pages", pages.toString())
        DetailRow("Language", language)
        DetailRow("Category", category)
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
            color = Color.Black,
            modifier = Modifier.weight(1f) // Sol tarafa daha fazla alan verir
        )
        Text(
            text = value,
            style = TextStyle(fontSize = 16.sp),
            color = Color.Gray,
            modifier = Modifier.weight(2f) // Sağ tarafa daha fazla alan verir
        )
    }
}
@Composable
fun ReviewContent(reviews:List<com.example.booknest.api.Models.Review>) {


    // LazyColumn'u doğrudan Tab içinde kullan
    Column() {
        reviews.forEach { review ->
            ReviewCard(review) // Burada ReviewCard'ı kullanıyorsunuz
        }
    }
}



@Composable
fun ReviewCard(review: com.example.booknest.api.Models.Review) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Kullanıcı Resmi (Sol Tarafta)
            Image(
                painter = if (!review.cover.isNullOrEmpty()) {
                    rememberImagePainter(data = review.cover)
                } else {
                    painterResource(id = R.drawable.outline_person_24) // Varsayılan görsel
                },
                contentDescription = "User Image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            // Yorum İçeriği
            Column(modifier = Modifier.weight(1f)) {
                // Kullanıcı adı
                Text(
                    text = review.username,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Yorum metni
                Text(
                    text = review.text,
                    style = TextStyle(fontSize = 14.sp),
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Yorumun tarihi
                Text(
                    text = "${getTimeAgo(review.date)}",
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                )
            }

            // Rating (Sağ Tarafta)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Rating Yıldızları
                RatingStars(rating = review.rating)

                // Yıldız Sayısı
                Text(
                    text = String.format("%.1f", review.rating),  // Format rating to 1 decimal place
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}



@Composable
fun RatingStars(rating: Float) {
    Row(horizontalArrangement = Arrangement.Start) {
        // Display 5 stars based on the rating value
        repeat(5) { index ->
            when {
                rating >= (index + 1) -> {
                    // Fully filled star
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Star",
                        tint = Color.Red,
                        modifier = Modifier.size(20.dp)
                    )
                }
                rating >= (index + 0.5f) -> {
                    // Half filled star
                    Icon(
                        imageVector = Icons.Filled.StarHalf,
                        contentDescription = "Half Star",
                        tint = Color.Red,
                        modifier = Modifier.size(20.dp)
                    )
                }
                else -> {
                    // Empty star
                    Icon(
                        imageVector = Icons.Filled.StarBorder,
                        contentDescription = "Empty Star",
                        tint = Color.Red,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

