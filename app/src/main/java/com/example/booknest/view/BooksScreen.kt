package com.example.booknest.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
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
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.palette.graphics.Palette
import com.example.booknest.Model.SearchResult
import com.example.booknest.R

@Composable
fun BooksScreen(navController: NavController,result: SearchResult.Book) {
    val bitmap: Bitmap = BitmapFactory.decodeResource(LocalContext.current.resources, result.imageResId)
    val palette = Palette.from(bitmap).generate()
    var userRating by remember { mutableFloatStateOf(0f) }
    var selectedStatus by remember { mutableStateOf("Want to Read") }
    var expanded by remember { mutableStateOf(false) }
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("ABOUT", "DETAILS","REVIEWS")

    // Durum seçenekleri
    val statusOptions = listOf("Want to Read", "Currently Reading", "I've Read")
    val bookDetails = BookDetails(
        title = "The Great Gatsby",
        publishDate = "April 10, 1925",
        publisher = "Charles Scribner's Sons",
        isbn = "9780743273565",
        series = null,
        language = "English",
        characters = listOf("Jay Gatsby", "Nick Carraway", "Daisy Buchanan")
    )
    val dominantColor = palette?.dominantSwatch?.rgb?.let { Color(it) } ?: Color.Yellow
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
                    .background(dominantColor)
                    .graphicsLayer {
                        shadowElevation = 150.dp.toPx()
                    }
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.TopStart).padding(top = 15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(35.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(dominantColor.copy(alpha = 0.3f))
                        .graphicsLayer {
                            shadowElevation = 150.dp.toPx()
                        }
                ) {
                    Image(
                        painter = painterResource(id = result.imageResId),
                        contentDescription = "Book Cover",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(290.dp)
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = result.title,
                style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.W500),
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "by ${result.author}",
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
                Column(modifier = Modifier.clickable { navController.navigate("comment") }) {
                    RatingStars(result.rating.toFloatOrNull() ?: 0f)
                    Text(
                        text = result.rating,
                        style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Medium),
                    )
                }

                Column {
                    Text(
                        text = "168,500 ratings",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                    )
                    Text(
                        text = "10,123 reviews",
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
                        onClick = { /* Güncelleme işlemi */ },
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
                0 -> (AboutContent())
                1 -> (DetailContent(   title = bookDetails.title,
                    publishDate = bookDetails.publishDate,
                    publisher = bookDetails.publisher,
                    isbn = bookDetails.isbn,
                    series = bookDetails.series,
                    language = bookDetails.language,
                    characters = bookDetails.characters))
                2 -> (ReviewContent())
            }

        }
    }
}
@Composable
fun AboutContent() {
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
                text = "A book description is a brief overview of the plot, main characters, and themes of the story. It's an important tool that helps in book promotion and sales. Many times, book descriptions also include information about the author. This helps to build credibility and establish a connection with the reader.",
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
    series: String?,
    language: String,
    characters: List<String>
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
        DetailRow("Series", series ?: "N/A")
        DetailRow("Language", language)
        DetailRow("Characters", if (characters.isNotEmpty()) characters.joinToString(", ") else "N/A")
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
fun ReviewContent() {

    // Örnek yorumlar listesi
    val reviews = listOf(
        Review(
            userName = "John Doe",
            userImageResId = R.drawable.azad,
            rating = 4.5f,
            comment =  "A wonderful book! The story was gripping and the characters were well-developed.",
            time = System.currentTimeMillis() - (2 * 1000 * 60 * 60 * 24) // Örnek: 2 gün önce
        ),
        Review(
            userName = "Jane Smith",
            userImageResId = R.drawable.azad,
            rating = 3.0f,
            comment = "The book was decent, but I felt the pacing was a bit slow.",
            time = System.currentTimeMillis() - (2 * 1000 * 60 * 60 * 24) // Örnek: 2 gün önce
        ),
        Review(
            userName = "John Doe",
            userImageResId = R.drawable.azad,
            rating = 4.5f,
            comment =  "A wonderful book! The story was gripping and the characters were well-developed.",
            time = System.currentTimeMillis() - (2 * 1000 * 60 * 60 * 24) // Örnek: 2 gün önce
    ),
        Review(
            userName = "Jane Smith",
            userImageResId = R.drawable.azad,
            rating = 3.0f,
            comment = "The book was decent, but I felt the pacing was a bit slow.",
            time = System.currentTimeMillis() - (2 * 1000 * 60 * 60 * 24) // Örnek: 2 gün önce
    )
    )

    // LazyColumn'u doğrudan Tab içinde kullan
    Column() {
        reviews.forEach { review ->
            ReviewCard(review) // Burada ReviewCard'ı kullanıyorsunuz
        }
    }
}



@Composable
fun ReviewCard(review: Review) {
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
                painter = painterResource(id = review.userImageResId),
                contentDescription = "User Image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            // Yorum İçeriği
            Column(modifier = Modifier.weight(1f)) {
                // Kullanıcı adı
                Text(
                    text = review.userName,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Yorum metni
                Text(
                    text = review.comment,
                    style = TextStyle(fontSize = 14.sp),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Yorumun tarihi
                Text(
                    text = "${getTimeAgo(review.time)}",
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
                    text = "${review.rating} / 5",
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

data class Review(
    val userName: String,
    val userImageResId: Int,
    val rating: Float,
    val comment: String,
    val time: Long, // Unix timestamp
)

fun getTimeAgo1(time: Long): String {
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
data class BookDetails(
    val title: String,
    val publishDate: String,
    val publisher: String,
    val isbn: String,
    val series: String?,
    val language: String,
    val characters: List<String>
)
