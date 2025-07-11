package com.example.booknest.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text


import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booknest.R
import com.example.booknest.ViewModel.LoginViewModel


@Composable
fun ProfileScreen(navController: NavController,viewModel: LoginViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileHeader(userName = viewModel.profileResponse.value?.username, userImageResId = viewModel.profileResponse.value?.avatar)
        Log.d("ProfileHeader", "User image link: ${viewModel.profileResponse.value?.avatar}")
        Spacer(modifier = Modifier.height(24.dp))
        StatsRow()
        Spacer(modifier = Modifier.height(24.dp))
        ActionSection(
            buttonTitles = listOf("Books Read", "Medals", "Friends", "Challenge"),
            onClick = { title ->
                when (title) {
                    "Books Read" -> navController.navigate("booksIveRead")
                    "Medals" -> navController.navigate("medals")
                    "Friends" -> navController.navigate("friends/Azad Kol")
                    "Challenge" -> navController.navigate("challenge")
                }
            }
        )
    }
}
