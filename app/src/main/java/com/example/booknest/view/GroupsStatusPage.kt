package com.example.booknest.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booknest.Model.DummyData
import com.example.booknest.Model.SearchResult
import com.example.booknest.R
import com.example.booknest.ui.theme.ButtonColor1
import com.example.booknest.ui.theme.PrimaryColor

@SuppressLint("MutableCollectionMutableState")
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
    var showBottomSheet by remember { mutableStateOf(false) }
    var readingStatuses by remember { mutableStateOf<List<Pair<SearchResult.Book, Int>>>(emptyList()) }

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
                        // Admin arayüzü
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
                        ) {
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

                        // Seçilen Kitap Bilgileri
                        selectedBook?.let { book ->
                            SelectedBookInfoCard(book,navController)
                        }

                    } else {
                        // Grup Üyesi Ekranı
                        if (selectedBook == null) {
                            Text(text = "No book selected yet", fontSize = 18.sp)
                        } else {
                            // Üye için seçilen kitap bilgilerini göster
                            SelectedBookInfoCard(selectedBook!!,navController)
                        }
                    }

                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().height(330.dp),
                            contentPadding = PaddingValues(bottom=10.dp)
                        ) {
                            items(readingStatuses) { (book, pagesRead) ->
                                val percentage = if (book.pageNumber > 0) {
                                    minOf((pagesRead * 100) / book.pageNumber, 100) // Yüzdeyi 100 ile sınırlıyoruz
                                } else 0

                                Card(
                                    modifier = Modifier.fillMaxWidth()

                                        .padding(start = 20.dp, end = 20.dp).size(width = 45.dp, height = 105.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    )
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(text = "Book: ${book.title}", fontSize = 18.sp)
                                        Text(text = "Pages Read: $pagesRead/${book.pageNumber}", fontSize = 16.sp)
                                        LinearProgressIndicator(
                                            progress = percentage / 100f, // Progress için 0-1 arası bir değer gerekiyor
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(8.dp)
                                                .clip(RoundedCornerShape(4.dp)),
                                            color = Color(0xFF3CB371), // Aktif track rengi
                                            trackColor = Color(0xFFD3D3D3) // Pasif track rengi
                                        )


                                        Text(text = "Completion: $percentage%", fontSize = 14.sp)
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End) {
                            Button(
                                onClick = { showBottomSheet = true },
                                colors = ButtonDefaults.buttonColors(containerColor =  Color(0xFF2E8B57)),
                                shape = RoundedCornerShape(5.dp),
                                modifier = Modifier
                                    .padding(16.dp, bottom = 100.dp, end = 25.dp)
                                    .width(175.dp)
                                    .height(45.dp)
                            ) {
                                Text("State your status")
                            }
                        }


                        if (showBottomSheet && selectedBook != null) {
                            BottomSheet_1(
                                selectedBook = selectedBook!!,
                                onDismiss = { showBottomSheet = false },
                                onSave = { pagesRead ->
                                    // Listeyi güncellerken eski veriyi koruyarak yeni veriyi ekliyoruz
                                    readingStatuses = readingStatuses.toMutableList().apply {
                                        add(selectedBook!! to pagesRead)
                                    }
                                    showBottomSheet = false
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet_1(
    selectedBook: SearchResult.Book,
    onDismiss: () -> Unit,
    onSave: (Int) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var pagesRead by remember { mutableStateOf("") }

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(0.5f),
        sheetState = sheetState,
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Update Status for ${selectedBook.title}",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )
            OutlinedTextField(
                value = pagesRead,
                onValueChange = { pagesRead = it },
                label = { Text("Pages Read") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(16.dp)
            )
            Button(
                onClick = {
                    val pages = pagesRead.toIntOrNull() ?: 0 // Geçersiz sayfa girişi olduğunda 0 yap
                    onSave(pages) // Sayfa okunduğunda status kaydedilir
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E8B57)),
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Save")
            }
        }
    }
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
fun SelectedBookInfoCard(book: SearchResult.Book,navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .clickable { navController.navigate("books/${book.id}/${book.title}/${book.author}/${book.imageResId}/${book.rating}/${book.pageNumber}") }

            ,
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth()
           ,
            verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = book.imageResId),
                contentDescription = book.title,
                modifier = Modifier.size(100.dp)
            )
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly) {
                Text(text = "Currently Reading Book:", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                Text(
                    text = book.title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                )
                Text(
                    text = "by ${book.author}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RatingStars(rating = book.rating.toFloatOrNull() ?: 0f)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = book.rating,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(28.dp))
                    Text(
                        text = "Page No: ${book.pageNumber}",
                        color = Color.Gray,
                        fontSize = 14.sp,
                    )
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
        colors = CardDefaults.cardColors(
            containerColor = PrimaryColor
        )

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

