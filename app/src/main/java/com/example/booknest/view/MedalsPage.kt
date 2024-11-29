package com.example.booknest.view

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import com.example.booknest.R  // Ensure this import matches your actual package
import androidx.navigation.NavController

@Composable
fun MedalsPage( navController: NavController) {
    // List of medals, using the same image for all
    val medals = listOf(
        "Gold Medal",
        "Silver Medal",
        "Bronze Medal",
        "Participation Medal"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Medals Earned",
            modifier = Modifier.padding(bottom = 24.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            medals.forEach { medal ->
                MedalItem(medalTitle = medal)
            }
        }
    }
}

@Composable
fun MedalItem(medalTitle: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // Medal icon from drawable (using the same "awards.png" for all medals)
            Image(
                painter = painterResource(id = R.drawable.awards),  // Using the "awards.png" image
                contentDescription = "$medalTitle Icon",
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp)),
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Medal title
            Text(
                text = medalTitle,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}
