package com.example.booknest.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.booknest.Model.Group
import com.example.booknest.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupInfoPage(navController: NavController, groupName: String) {
    var aboutExpanded by remember { mutableStateOf(false) }
    var rulesExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .border(1.dp, Color.Gray, RoundedCornerShape(95.dp))
                .clip(RoundedCornerShape(95.dp))
                .background(Color.White)
                .size(90.dp)){
            Image(
                painter = painterResource(id = R.drawable.sharp_groups_24),
                contentDescription = "User Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(90.dp)
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))
        // Group Name Card
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = groupName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        // About Section
        ExpandableCard(
            title = "About",
            content = "This group is focused on discussing books in the genre of fantasy and science fiction. Join us to share your thoughts!",
            expanded = aboutExpanded,
            onExpandChange = { aboutExpanded = it }
        )

        // Rules Section
        ExpandableCard(
            title = "Rules",
            content = "1. Be respectful\n2. No spoilers without warning\n3. Stay on topic",
            expanded = rulesExpanded,
            onExpandChange = { rulesExpanded = it }
        )

        // Members Section
        Text(
            text = "Members",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth().height(400.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(10) { index ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.outline_person_24),
                            contentDescription = "Member Avatar",
                            modifier = Modifier
                                .size(40.dp)
                                .padding(end = 8.dp)
                        )
                        Text(
                            text = "Member $index",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun ExpandableCard(title: String, content: String, expanded: Boolean, onExpandChange: (Boolean) -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    modifier = Modifier.clickable { onExpandChange(!expanded) },
                    painter = painterResource(id = if (expanded) R.drawable.baseline_expand_less_24 else R.drawable.baseline_expand_more_24),
                    contentDescription = null
                )
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = content,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
