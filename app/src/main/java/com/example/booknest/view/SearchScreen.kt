package com.example.booknest.view

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booknest.Model.DummyData
import com.example.booknest.Model.SearchResult

@Composable
fun SearchScreen (navController: NavController,modifier: Modifier=Modifier){
    var allResults = DummyData().dummyData
    var searchQuery by remember{ mutableStateOf("") }
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
    Column (modifier=Modifier.fillMaxHeight(),){
        TextField(
            value = searchQuery,
            onValueChange = {searchQuery=it},
            placeholder = {Text("Search users or books")},
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        )
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