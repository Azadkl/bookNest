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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.palette.graphics.Palette
import com.example.booknest.Model.SearchResult

@Composable
fun BooksScreen(navController: NavController,result: SearchResult.Book) {
    val bitmap: Bitmap = BitmapFactory.decodeResource(LocalContext.current.resources, result.imageResId)
    val palette = Palette.from(bitmap).generate()
    var userRating by remember { mutableFloatStateOf(0f) }

    val dominantColor = palette?.dominantSwatch?.rgb?.let { Color(it) } ?: Color.Yellow
    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally){

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
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = result.title,
            style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.W500),
            )
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "by ${result.author}",
            style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.W400),
        )
        Spacer(modifier = Modifier.height(35.dp))
        Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.fillMaxWidth(0.9f))
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.clickable {  navController.navigate("comment") }) {
                RatingStars(result.rating.toFloatOrNull()?:0f)
                Text(text = result.rating,
                    style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Medium),
                )
            }

            Column {
                Text(text = "168,500 ratings",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                )
                Text(text = "10,123 reviews",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
                )
            }

        }
        Spacer(modifier = Modifier.height(10.dp))

        Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.fillMaxWidth(0.9f))
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {},
            modifier = Modifier
                .size(width = 250.dp, height = 50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2E8B57)
            ),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(
                fontSize = 25.sp,
                text = "Want to Read",
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            fontSize = 20.sp,
            text = "Rate this book:",
            color = Color.Black
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
               ,
            horizontalArrangement = Arrangement.Center
        ) {
            // Render interactive stars for user rating
            repeat(5) { index ->
                val starRating = index+1
                Icon(
                    imageVector = when {
                        userRating >= starRating -> Icons.Filled.Star
                        userRating >= starRating - 0.5f -> Icons.Filled.StarHalf
                        else -> Icons.Filled.StarBorder
                    },
                    contentDescription = "Star $starRating",
                    tint = Color.Red,
                    modifier = Modifier.size(30.dp)
                        .clickable { userRating = starRating.toFloat() }
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