package com.example.booknest.view

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booknest.Model.DummyData
import com.example.booknest.Model.SearchResult
import com.example.booknest.R
import com.example.booknest.ui.theme.ButtonColor1
import com.example.booknest.ui.theme.ButtonColor2
import com.example.booknest.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen (navController: NavController,modifier: Modifier=Modifier){

    LazyColumn(modifier=Modifier
        .fillMaxSize()
        .padding(top = 120.dp),
        horizontalAlignment = Alignment.Start,){
       item {
           Column {
               Row (modifier=Modifier.fillMaxWidth()
                   .padding(start = 10.dp, end = 10.dp),
                   verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.SpaceBetween){

                   Text(text = "MOST READ THIS WEEK", style = TextStyle(
                       fontSize = 15.sp,
                       fontWeight = FontWeight.Medium,
                       color = Color.LightGray
                   )
                   )
                   Text(modifier = Modifier.clickable {
                       val books = DummyData().dummyData.filterIsInstance<SearchResult.Book>()
                       navController.navigate("book_list_screen/Most Read This Week") {
                       }
                   },
                       text = "SEE ALL", style = TextStyle(
                           fontSize = 15.sp,
                           fontWeight = FontWeight.Medium,
                           color = Color.LightGray
                       )
                   )
               }
               LazyRow (modifier=Modifier.fillMaxWidth()
                   .padding(top = 15.dp, bottom = 15.dp)){
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.houseoflame),
                            contentDescription = "Book Cover",
                            modifier = Modifier
                                .size(160.dp)
                        )
                    }

                   item {
                       Image(
                           painter = painterResource(id = R.drawable.images),
                           contentDescription = "Book Cover",
                           modifier = Modifier
                               .size(160.dp)
                       )
                   }

                   item {
                       Image(
                           painter = painterResource(id = R.drawable.farelerveinsanlar),
                           contentDescription = "Book Cover",
                           modifier = Modifier
                               .size(160.dp)
                       )
                   }

                   item {
                       Image(
                           painter = painterResource(id = R.drawable.images),
                           contentDescription = "Book Cover",
                           modifier = Modifier
                               .size(160.dp)
                       )
                   }
               }
           }


       }
        item {
            Column {
                Row (modifier=Modifier.fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween){

                    Text(text = "NEW RELEASES THIS MONTH", style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.LightGray
                    )
                    )
                    Text(modifier = Modifier.clickable { val books = DummyData().dummyData.filterIsInstance<SearchResult.Book>()
                            navController.navigate("book_list_screen/New Releases This Month") {
                            }
                    },
                        text = "SEE ALL", style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.LightGray
                        )
                    )
                }
                LazyRow(modifier=Modifier.fillMaxWidth()
                    .padding(top = 15.dp, bottom = 15.dp)) {
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.images),
                            contentDescription = "Book Cover",
                            modifier = Modifier
                                .size(160.dp)
                        )
                    }

                    item {
                        Image(
                            painter = painterResource(id = R.drawable.farelerveinsanlar),
                            contentDescription = "Book Cover",
                            modifier = Modifier
                                .size(160.dp)
                        )
                    }

                    item {
                        Image(
                            painter = painterResource(id = R.drawable.images),
                            contentDescription = "Book Cover",
                            modifier = Modifier
                                .size(160.dp)
                        )
                    }

                    item {
                        Image(
                            painter = painterResource(id = R.drawable.houseoflame),
                            contentDescription = "Book Cover",
                            modifier = Modifier
                                .size(160.dp)
                        )
                    }
                }
            }


        }
        item {
            Column {
                Row (modifier=Modifier.fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween){

                    Text(text = "NEW RELEASES THIS YEAR", style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.LightGray
                    )
                    )
                    Text(modifier = Modifier.clickable { val books = DummyData().dummyData.filterIsInstance<SearchResult.Book>()
                        navController.navigate("book_list_screen/New Releases This Year") {
                        } },
                        text = "SEE ALL", style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.LightGray
                        )
                    )
                }
                LazyRow(modifier=Modifier.fillMaxWidth()
                    .padding(top = 15.dp, bottom = 15.dp)) {
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.farelerveinsanlar),
                            contentDescription = "Book Cover",
                            modifier = Modifier
                                .size(160.dp)
                        )
                    }

                    item {
                        Image(
                            painter = painterResource(id = R.drawable.houseoflame),
                            contentDescription = "Book Cover",
                            modifier = Modifier
                                .size(160.dp)
                        )
                    }

                    item {
                        Image(
                            painter = painterResource(id = R.drawable.farelerveinsanlar),
                            contentDescription = "Book Cover",
                            modifier = Modifier
                                .size(160.dp)
                        )
                    }

                    item {
                        Image(
                            painter = painterResource(id = R.drawable.images),
                            contentDescription = "Book Cover",
                            modifier = Modifier
                                .size(160.dp)
                        )
                    }
                }
            }


        }
    }
}
