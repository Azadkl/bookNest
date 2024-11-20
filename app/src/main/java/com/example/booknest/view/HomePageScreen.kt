package com.example.booknest.view

import androidx.compose.material3.Typography
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.booknest.Book
import com.example.booknest.R
import com.example.booknest.ui.theme.ButtonColor1
import com.example.booknest.ui.theme.PrimaryColor


@Composable
fun HomePageScreen(modifier: Modifier = Modifier) {
    var search by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            //.fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "what's happening around?",
                        modifier = Modifier.padding(bottom = 10.dp),
                        style = TextStyle(fontSize = 25.sp)
                    )
                    Divider(
                        color = Color.Black,
                        thickness = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            items(3) {
                StatusCard()
            }
        }
    }
}

@Composable
fun StatusCard() {
    var dropdownExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp)
            .padding(vertical = 8.dp)
            .border(1.dp, Color.Gray),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Emir gave 5 stars to Of Mice and Men by John Steinback",
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.farelerveinsanlar),
                    contentDescription = "Book Cover Image",
                    modifier = Modifier
                        .size(80.dp)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Of Mice and Man",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "by John Steinback",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {},
                            modifier = Modifier
                                .size(width = 120.dp, height = 32.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2E8B57)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                fontSize = 13.sp,
                                text = "Want to Read",
                                color = Color.White
                            )
                        }


                        Button(
                            onClick = { dropdownExpanded = !dropdownExpanded },
                            modifier = Modifier
                                .size(width = 32.dp, height = 32.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2E8B57)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("⋮", color = Color.White)
                        }
                    }


                    if (dropdownExpanded) {
                        DropdownMenu(
                            expanded = dropdownExpanded,
                            onDismissRequest = { dropdownExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Already Read") },
                                onClick = {

                                    dropdownExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Reading Now") },
                                onClick = {

                                    dropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

