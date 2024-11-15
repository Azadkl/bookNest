package com.example.booknest.view

import android.graphics.drawable.Icon
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booknest.Model.DummyData
import com.example.booknest.Model.SearchResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen (navController: NavController,modifier: Modifier=Modifier){
    var allResults = DummyData().dummyData
    var searchQuery by remember{ mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val filteredResult= if (searchQuery.isEmpty()){
        emptyList<SearchResult>()
            }else{
        allResults.filter {
            when(it){
                is SearchResult.User -> it.name.contains(searchQuery, ignoreCase = true)
                is SearchResult.Book -> it.title.contains(searchQuery, ignoreCase = true)
            }
    }

    }
    Column (modifier=Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally){
        SearchBar(
            modifier = Modifier

                .border(width = 0.dp, color = Color.Transparent),
            query = searchQuery,
            onQueryChange ={searchQuery=it},
            onSearch ={active=false },
            placeholder = { Text(text = "Search users or books")},
            active =active , onActiveChange ={active=it},
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
            },
            trailingIcon = {
                if(active){
                    Icon(
                        modifier = Modifier.clickable {
                            if (searchQuery.isNotEmpty()){
                                searchQuery = ""
                            }else{
                                active=false
                            }
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "CloseIcon"
                    )
                }
            }) {
            LazyColumn {
                items(filteredResult){result->
                    when(result){
                        is SearchResult.User->{
                            ListItem(
                                headlineContent = { Text(result.name)},
                                modifier=Modifier.clickable {

                                }
                            )
                        }
                        is SearchResult.Book->{
                            ListItem(
                                headlineContent = { Text(result.title)},
                                supportingContent = { Text(result.author)},
                                modifier=Modifier.clickable {  }
                            )
                        }
                    }

                }
            }
        }
    }




    }
