package com.example.booknest.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Group
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun SettingsScreen() {
    var isSheetVisible by remember { mutableStateOf(false) }
    val sheetHeight by animateDpAsState(targetValue = if (isSheetVisible) 400.dp else 0.dp)

    Box(modifier = Modifier.fillMaxSize()) {
        // Main content for current screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Settings Screen", fontSize = 24.sp)

            Button(onClick = { isSheetVisible = !isSheetVisible }) {
                Text(text = "More", fontSize = 20.sp)
            }
        }

        // Sliding bottom sheet
        if (isSheetVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)) // Dimmed background
                    .padding(bottom = sheetHeight)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(sheetHeight)
                    .background(Color.White)
                    .align(Alignment.BottomCenter)
            ) {
                MoreOptionsContent()
            }
        }
    }
}

@Composable
fun MoreOptionsContent() {
    val circleSize = 120.dp
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 32.dp, vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Text(
            text = "More",
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 40.dp)
        )

        // First row of circles
        Row(
            horizontalArrangement = Arrangement.spacedBy(40.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleWithText(icon = Icons.Default.Person, label = "Profile", size = circleSize)
            CircleWithText(icon = Icons.Default.Settings, label = "Settings", size = circleSize)
        }

        // Second row of circles
        Row(
            horizontalArrangement = Arrangement.spacedBy(40.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleWithText(icon = Icons.Default.Group, label = "Groups", size = circleSize)
            CircleWithText(icon = Icons.Default.Group, label = "Challenges", size = circleSize)
        }
    }
}

@Composable
fun CircleWithText(icon: ImageVector, label: String, size: Dp) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.size(size)
    ) {
        IconButton(
            onClick = { /* Handle action */ },
            modifier = Modifier
                .size(size)
                .background(Color.White, CircleShape)
                .padding(20.dp)
        ) {
            Icon(imageVector = icon, contentDescription = label, modifier = Modifier.size(size / 2))
        }
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen()
}
