package com.example.booknest.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booknest.Model.DummyData
import com.example.booknest.Model.SearchResult
import com.example.booknest.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsStatusPage(
    navController: NavController,
    groupName: String,
    isAdmin: Boolean,
    books: List<SearchResult.Book> // Kitapları alıyoruz
) {
    var selectedBook by remember { mutableStateOf<SearchResult.Book?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                        Text(
                            text = groupName,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle info icon click */ }) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = "Info")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White)
            )
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = PrimaryColor
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isAdmin) {

                        SearchBar(
                            modifier = Modifier
                                .let { if (active) it.fillMaxWidth() else it.width(350.dp) }
                                .border(width = 0.dp, color = Color.Transparent),
                            query = searchQuery,
                            onQueryChange = { searchQuery = it },
                            onSearch = { active = false },
                            placeholder = { Text(text = "Search books") },
                            active = active,
                            onActiveChange = { active = it },
                            colors = SearchBarDefaults.colors(
                                containerColor = Color.White,
                                dividerColor = Color.Black
                            ),
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
                            }
                        ){
                            // Arama sorgusuna göre filtrelenmiş kitaplar
                            val filteredBooks = if (searchQuery.isEmpty()) {
                                emptyList() // Eğer arama yapılmadıysa boş liste döndür
                            } else {
                                books.filter {
                                    it.title.contains(searchQuery, ignoreCase = true)
                                }
                            }


                            BookSelectionList(filteredBooks, selectedBook) { book ->
                                selectedBook = book
                                active = false
                            }
                        }
                        // Yönetici için Arama Çubuğu
                        selectedBook?.let {
                            // Kitap seçildiyse, BookItem ile göster
                            BookItem(book = it, isSelected = true, onBookClick = {})
                        }


                    } else {
                        // Grup Üyesi Ekranı
                        if (selectedBook == null) {
                            Text(text = "No book selected yet", fontSize = 18.sp)
                        } else {
                            // Üyeler için de kitap görünümünü BookItem olarak göster
                            BookItem(book = selectedBook!!, isSelected = true, onBookClick = {})
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun BookSelectionList(
    books: List<SearchResult.Book>,
    selectedBook: SearchResult.Book?,
    onBookSelected: (SearchResult.Book) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        item{
            books.forEach { book ->
                BookItem(book, isSelected = selectedBook == book) {
                    onBookSelected(book)
                }
            }
        }

    }
}

@Composable
fun BookItem(book: SearchResult.Book, isSelected: Boolean, onBookClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onBookClick() },

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = book.imageResId),
                contentDescription = book.title,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = book.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "by ${book.author}", fontSize = 14.sp, color = Color.Gray)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RatingStars(book.rating.toFloatOrNull() ?: 0f)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = book.rating,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = Color.Green
                )
            }
        }
    }
}

