package com.example.booknest.view

import android.media.Image
import android.net.Uri
import android.util.Log
import android.widget.Space
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.vector.DefaultTintColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.substring
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.booknest.Model.DummyData
import com.example.booknest.Model.SearchResult
import com.example.booknest.NavItem
import com.example.booknest.R
import com.example.booknest.ViewModel.LoginViewModel
import com.example.booknest.ui.theme.ButtonColor1
import com.example.booknest.ui.theme.PrimaryColor



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarScreen(mainNavController:NavController,modifier: Modifier=Modifier,viewModel: LoginViewModel) {
    var showDialog by remember { mutableStateOf(false) } // Dialog kontrolü için state
    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Search", Icons.Default.Search),
        NavItem("MyBooks", Icons.Default.Book),
        NavItem("More", Icons.Default.Menu),

        )

    val contentPadding = PaddingValues(0.dp)
    var navController = rememberNavController()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    var selectedIndex by remember { mutableIntStateOf(0) }
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
    Scaffold (
        topBar = {
            Row (modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically) {
                val currentRoute by navController.currentBackStackEntryFlow.collectAsState(initial = null)

                if (currentRoute?.destination?.route in listOf("search","Home","myBooks")){
                    Box(
                        modifier = Modifier
                            .let { if (active) it.size(0.dp) else it}
                            .padding(top = 30.dp)
                            .clip(shape = CircleShape)
                            .clickable { navController.navigate("profile") }
                            .size(56.dp)
                            .background(Color.White)
                        ,
                    ) {

                        Image(painter = rememberImagePainter(viewModel.profileResponse.value?.avatar), contentDescription ="User Image" ,
                            contentScale = ContentScale.Crop,
                            modifier=Modifier.fillMaxSize()
                                .clip(CircleShape)
                        )
                    }
                    SearchBar(
                        modifier = Modifier
                            .let { if (active) it.fillMaxWidth() else it.width(273.dp) }
                            .border(width = 0.dp, color = Color.Transparent),
                        query = searchQuery,
                        onQueryChange ={searchQuery=it},
                        onSearch ={active=false },
                        placeholder = { Text(text = "Search users or books")},
                        active =active , onActiveChange ={active=it},
                        colors = SearchBarDefaults.colors(
                            containerColor = Color.White,
                            dividerColor = Color.Black,

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
                        })

                    {      // Eğer sonuçlar boşsa, kullanıcıya mesaj göster ve buton ekle
                        val users by viewModel.usersResponse
                        val books by viewModel.bookResponse
                        val filteredBooks = books?.filter { book ->
                            book.title.contains(searchQuery, ignoreCase = true)
                        } ?: emptyList()
                        // Arama metnine göre kullanıcıları filtrele
                        val filteredUsers = users?.filter { user ->
                            user.username.contains(searchQuery, ignoreCase = true)
                        } ?: emptyList()
                        LaunchedEffect(Unit) {
                            viewModel.fetchUsers()  // Bu satır verileri çekmeye başlar
                            viewModel.fetchBook()
                        }
                        if ((filteredUsers.isEmpty() && filteredBooks.isEmpty()) || searchQuery=="") {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "If you think the book you're looking for is missing, please contact us to add it.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 20.dp)
                                )
                                Button(
                                    onClick = {
                                        showDialog=true
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF2E8B57)
                                    )
                                ) {
                                    Text("Contact Admin")
                                }
                                // Dialog gösterme
                                if (showDialog) {
                                    ContactAdminDialog(
                                        onConfirm = {isbn ->
                                            showDialog = false // Dialogu kapat
                                        },
                                        onDismiss = { showDialog = false },
                                        viewModel// Dialogu kapatma
                                    )
                                }
                            }
                        }
                        else{
                            var isLoading by remember { mutableStateOf(true) }


                            if (users != null) {
                                isLoading = false
                            }

                            Log.d("Users", "Fetched users: $users")
                            if(isLoading){
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                            else{
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 10.dp),
                                    contentPadding = PaddingValues(bottom = 100.dp),
                                ) {


                                    // Filtrelenmiş kullanıcıları listele
                                    items(filteredUsers) { user ->
                                        ListItem(
                                            headlineContent = {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(start = 10.dp)
                                                ) {
                                                    Image(
                                                        painter = rememberImagePainter(user.avatar),
                                                        contentDescription = "User Image",
                                                        contentScale = ContentScale.Crop,
                                                        modifier = Modifier
                                                            .size(50.dp)
                                                            .clip(CircleShape)
                                                            .offset(y=(1.dp))
                                                    )
                                                    Spacer(modifier = Modifier.width(20.dp))
                                                    Text(user.username)
                                                }
                                            },
                                            colors = ListItemDefaults.colors(
                                                containerColor = PrimaryColor,
                                                headlineColor = Color.Black,
                                            ),
                                            modifier = Modifier
                                                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                                                .height(70.dp)
                                                .clip(shape = RoundedCornerShape(15.dp))
                                                .clickable {
                                                    isLoading = true
                                                    navController.navigate("otherProfile/${user.username}/${Uri.encode(user.avatar)}")
                                                }
                                        )
                                    }


                                    items(filteredBooks) { book ->
                                        ListItem(
                                            headlineContent = {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(start = 2.dp)
                                                ) {
                                                    Image(
                                                        painter = rememberAsyncImagePainter(book.cover),
                                                        contentDescription = "Book Cover",
                                                        modifier = Modifier.size(70.dp)
                                                    )
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Column(modifier = Modifier.weight(1f)) {
                                                        Text(book.title)
                                                        Text("${book.author}", style = MaterialTheme.typography.bodySmall)
                                                    }
                                                    Text(
                                                        text = book.rating.toString().substring(0,3),
                                                        style = MaterialTheme.typography.bodySmall,
                                                        color = Color.Red
                                                    )
                                                }
                                            },
                                            colors = ListItemDefaults.colors(
                                                containerColor = PrimaryColor,
                                                headlineColor = Color.Black,
                                            ),
                                            modifier = Modifier
                                                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                                                .height(70.dp)
                                                .clip(shape = RoundedCornerShape(15.dp))
                                                .clickable {
                                                    navController.navigate("books/${Uri.encode(book.isbn)}/${Uri.encode(book.title)}/${Uri.encode(book.author)}/${Uri.encode(book.cover)}/${Uri.encode(book.description)}/${book.rating}/${book.pages}/${Uri.encode(book.category)}/${Uri.encode(book.language)}/${Uri.encode(book.publishedDate)}/${Uri.encode(book.publisher)}")
                                                }
                                        )
                                    }

                                    // Eğer kitaplar yoksa, kullanıcıya mesaj göster
                                    if (filteredBooks.isEmpty() && filteredUsers.isEmpty()) {
                                        item {
                                            Text("No results found", modifier = Modifier.fillMaxSize())
                                        }
                                    }
                                }
                            }
                        }


                    }
                    Box(
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .clip(shape = CircleShape)
                            .clickable { navController.navigate("notifications") }
                            .size(56.dp)
                            .background(Color.White)
                        ,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                }
            } },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            ){
                NavigationBar(
                    modifier=Modifier.fillMaxWidth().height(90.dp),
                    containerColor = Color.White,) {
                    navItemList.forEachIndexed { index, navItem ->
                        NavigationBarItem(
                            modifier = Modifier.padding(top = 30.dp),
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                                when (index) {
                                    0 -> navController.navigate("Home") // Home sayfasına yönlendir
                                    1 -> navController.navigate("search") // Notifications sayfasına yönlendir
                                    2 -> navController.navigate("myBooks") // MyBooks sayfasına yönlendir
                                    3 -> {

                                        showBottomSheet = true
                                    }
                                }
                            },
                            icon = {
                                Icon(imageVector = navItem.icon, contentDescription = "home")
                            },
                            label = {
                                Text(text = navItem.label)
                            },
                            colors = NavigationBarItemColors(
                                selectedIndicatorColor = PrimaryColor,
                                selectedIconColor = DefaultShadowColor,
                                unselectedIconColor = DefaultShadowColor,
                                selectedTextColor = DefaultShadowColor,
                                unselectedTextColor = DefaultShadowColor,
                                disabledTextColor = DefaultShadowColor,
                                disabledIconColor = DefaultShadowColor
                            )

                        )
                    }
                }
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    modifier = Modifier.fillMaxHeight(),
                    sheetState = sheetState,
                    onDismissRequest = { showBottomSheet = false },
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = modifier
                                        .align(Alignment.CenterHorizontally)
                                        .border(1.dp, Color.Gray, RoundedCornerShape(95.dp))
                                        .clip(RoundedCornerShape(95.dp))
                                        .size(80.dp)
                                        .clickable {

                                            viewModel.fetchProfile()
                                            val user = viewModel.profileResponse.value
                                            Log.d("LoginViewModel", "Fetched Profile Data: $user")

                                            if (user != null ) {
                                                navController.navigate("profile")
                                                showBottomSheet = false // Alt menüyü kapat

                                            }
                                            else {
                                                Log.d("LoginViewModel", "Failed to fetch profile data.")
                                            }
                                        }
                                ) {
                                    Image(
                                        painter = rememberImagePainter(viewModel.profileResponse.value?.avatar),
                                        contentDescription = "User Profile Image",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .size(80.dp)
                                    )
                                }
                                Text(
                                    text = "My Profile",
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )




                            }


                            CustomBox(modifier, "Challenge", imageResId = R.drawable.target,navController = navController,"challenge",onNavigate = { route ->
                                showBottomSheet = false
                                navController.navigate(route)
                            })
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            CustomBox(modifier, "Friends", imageResId = R.drawable.outline_group_24,navController = navController,"friends/Azad Kol",onNavigate = { route ->
                                showBottomSheet = false
                                navController.navigate(route)
                            })
                            CustomBox(modifier, "Awards", imageResId = R.drawable.awards,navController = navController,"medals",onNavigate = { route ->
                                showBottomSheet = false
                                navController.navigate(route)
                            })
                            CustomBox(modifier, title = "Groups", imageResId = R.drawable.sharp_groups_24, navController = navController,"groups",onNavigate = { route ->
                                showBottomSheet = false
                                navController.navigate(route)
                            })
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {

                            CustomBox(modifier, "Settings", imageResId = R.drawable.settings,navController = navController,"settings",onNavigate = { route ->
                                showBottomSheet = false
                                navController.navigate(route)
                            })
                        }
                    }
                }
            }

        },

        content ={ paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                color = PrimaryColor
            ) {
                NavHost(navController = navController, startDestination = "Home") {
                    composable("Home") { HomePageScreen() }
                    composable("profile") { ProfileScreen(navController,viewModel=viewModel) }
                    composable("search") { SearchScreen(navController) }
                    composable("myBooks") { MyBooksPage(navController, viewModel = viewModel) }
                    composable("booksIveRead") { BooksIveRead(viewModel = viewModel,navController=navController) }
                    composable("booksIWantToRead") { ToRead(viewModel = viewModel,navController=navController) }
                    composable("currentlyReading") { ReadingNow(viewModel = viewModel,navController=navController) }
                    composable("search_screen") { SearchScreen(navController = navController) }
                    composable("settings"){ SettingsScreen(mainNavController,viewModel=viewModel) }
                    composable("groups") { GroupsPage(navController) }
                    composable("friends/{currentUser}") { backStackEntry ->
                        val currentUser = backStackEntry.arguments?.getString("currentUser") ?: ""
                        FriendsPage(navController = navController, currentUser = currentUser)
                    }
                    composable("challenge") { Challenge(navController) }
                    composable("medals") { MedalsPage(navController) }
                    composable("notifications"){ NotificationsScreen(navController) }
                    composable(
                        route = "books/{isbn}/{title}/{author}/{cover}/{description}/" +
                                "{rating}/{pages}/{category}/{language}/{publishedDate}" +
                                "/{publisher}",
                        arguments = listOf(
                            navArgument("isbn") { type = NavType.StringType },
                            navArgument("title") { type = NavType.StringType },
                            navArgument("author") { type = NavType.StringType },
                            navArgument("cover") { type = NavType.StringType },
                            navArgument("description") { type = NavType.StringType },
                            navArgument("rating") { type = NavType.StringType },
                            navArgument("pages") { type = NavType.IntType },
                            navArgument("category") { type = NavType.StringType },
                            navArgument("language") { type = NavType.StringType },
                            navArgument("publishedDate") { type = NavType.StringType },
                            navArgument("publisher") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val isbn = backStackEntry.arguments?.getString("isbn") ?: ""
                        val title = backStackEntry.arguments?.getString("title") ?: "Unknown Title"
                        val author = backStackEntry.arguments?.getString("author") ?: "Unknown Author"
                        val cover = backStackEntry.arguments?.getString("cover") ?: ""
                        val description = backStackEntry.arguments?.getString("description") ?: ""
                        val rating = backStackEntry.arguments?.getString("rating")?.toDoubleOrNull() ?: 0.0
                        val pages = backStackEntry.arguments?.getInt("pages") ?: -1
                        val category = backStackEntry.arguments?.getString("category") ?: "Unknown Category"
                        val language = backStackEntry.arguments?.getString("language") ?: "Unknown Language"
                        val publishedDate = backStackEntry.arguments?.getString("publishedDate") ?: "Unknown Date"
                        val publisher = backStackEntry.arguments?.getString("publisher") ?: "Unknown Publisher"


                        BooksScreen(
                            navController = navController,
                            isbn = isbn,
                            title = title,
                            author = author,
                            cover = cover,
                            description=description,
                            rating = rating.toString(),
                            pages = pages,
                            category = category,
                            language = language,
                            publishedDate = publishedDate,
                            publisher = publisher,
                            viewModel = viewModel
                        )
                    }
                    composable("book_list_screen/{title}") { backStackEntry ->
                        val title = backStackEntry.arguments?.getString("title") ?: "Books"
                        val books = DummyData().dummyData.filterIsInstance<SearchResult.Book>() // Listeyi filtrele
                        BookListScreen(navController, title, books)
                    }
                    composable("comment"){ CommentScreen(navController) }
                    composable(
                        route = "status/{groupName}",
                        arguments = listOf(navArgument("groupName") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val groupName = backStackEntry.arguments?.getString("groupName") ?: "Group"
                        val isAdmin = true // Bu değeri, grup yöneticisini belirleyen mantığa göre değiştirebilirsiniz
                        val dummyData = DummyData().booksOnly // DummyData'dan kitapları alıyoruz
                        GroupsStatusPage(navController = navController, groupName = groupName,isAdmin=isAdmin,books=dummyData)
                    }
                    composable(
                        route = "info/{groupName}",
                        arguments = listOf(navArgument("groupName") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val groupName = backStackEntry.arguments?.getString("groupName") ?: "Group"
                        GroupInfoPage(navController = navController, groupName=groupName)
                    }


                    composable("otherProfile/{userName}/{userImageResId}", arguments = listOf(
                        navArgument("userName") { type = NavType.StringType },
                        navArgument("userImageResId") { type = NavType.StringType }
                    )) { backStackEntry ->
                        val userName = backStackEntry.arguments?.getString("userName") ?: ""
                        val userImageResId = backStackEntry.arguments?.getString("userImageResId") ?: ""
                        OtherProfilePage(userName = userName, userImageResId = userImageResId, navController = navController, currentUser = userName,viewModel=viewModel)
                    }

                }
            }

        })
}


@Composable
fun ContactAdminDialog(
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
    viewModel: LoginViewModel
) {
    var bookTitle by remember { mutableStateOf("") }
    var authorName by remember { mutableStateOf("") }
    var isbn by remember { mutableStateOf("") }
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.setErrorMessage("")
    }

    AlertDialog(
        onDismissRequest = {}, // Kullanıcı sadece Cancel butonuyla dialog'u kapatsın
        containerColor = Color.White,
        title = { Text("Contact Admin") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // ISBN
                Text(text = "ISBN:")
                TextField(
                    value = isbn,
                    onValueChange = { isbn = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                // Hata veya başarı mesajı
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = if (errorMessage.contains("success", ignoreCase = true)) Color.Green else Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (isbn.isNotBlank()) {
                        viewModel.postBook(isbn) // Backend çağrısı yapılıyor
                        // Dialog'u açık tutmak için hiçbir şey yapılmıyor
                    }
                },
                enabled = isbn.isNotBlank() && !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E8B57)
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Submit")
                }
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss, // Kullanıcı Cancel ile dialog'u kapatabilir
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E8B57)
                )
            ) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun CustomBox(modifier: Modifier = Modifier, title: String, imageResId: Int,navController: NavController,route:String,onNavigate: (String) -> Unit,size:Dp=60.dp) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .border(1.dp, Color.Gray, RoundedCornerShape(95.dp))
                .clip(RoundedCornerShape(95.dp))
                .size(80.dp)
                .clickable { onNavigate(route) }
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = title,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(size)
                ,
            )
        }
        Text(
            text = title,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

