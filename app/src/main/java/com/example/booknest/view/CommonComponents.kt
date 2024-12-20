package com.example.booknest.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter

@Composable
fun ProfileHeader(userName: String?, userImageResId: String?) {
    Column(

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Spacer(modifier = Modifier.height(12.dp))
        userImageResId?.let {
            Box(
                modifier = Modifier
                    .size(100.dp) // Dış boyutu belirtiyoruz
                    .clip(RoundedCornerShape(95.dp)) // Resmin yuvarlatma şekli
                    .border(1.dp, Color.Gray, RoundedCornerShape(95.dp)) // Çerçeve ekliyoruz
            ) {
                Image(
                    painter = rememberImagePainter(userImageResId),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .fillMaxSize() // Resmi kutunun boyutuna göre doldur
                        .offset(y = (2).dp), // Resmi yukarı doğru kaydırarak alttan kırp
                    contentScale = ContentScale.Crop
                )
            }

        }

        Spacer(modifier = Modifier.height(12.dp))
        userName?.let {
            Text(
                text = it,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

    }
}

@Composable
fun StatsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Stat(title = "Books Read", value = 35)
        VerticalDivider()
        Stat(title = "Medals", value = 5)
        VerticalDivider()
        Stat(title = "Friends", value = 128)
    }
}

@Composable
fun Stat(title: String, value: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value.toString(), fontSize = 23.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Text(text = title, fontSize = 15.sp, color = Color.Gray)
    }
}

@Composable
fun ActionSection(buttonTitles: List<String>, onClick: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        buttonTitles.forEach { title ->
            ActionButton(title = title, onClick = { onClick(title) })
        }
    }
}

@Composable
fun ActionButton(title: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(0.8f),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF4A460),
            contentColor = Color.White
        )
    ) {
        Text(text = title, fontSize = 16.sp)
    }
}
