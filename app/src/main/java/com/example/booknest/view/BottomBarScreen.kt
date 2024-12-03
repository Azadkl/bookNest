package com.example.booknest.view

import android.media.Image
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
import androidx.compose.material3.Button
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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.booknest.Model.DummyData
import com.example.booknest.Model.SearchResult
import com.example.booknest.NavItem
import com.example.booknest.R
import com.example.booknest.ui.theme.ButtonColor1
import com.example.booknest.ui.theme.PrimaryColor



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarScreen(navController: NavController,modifier: Modifier=Modifier) {
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

                        Image(painter = painterResource(id=R.drawable.azad), contentDescription ="User Image" ,
                            contentScale = ContentScale.Fit,
                            modifier=Modifier.size(56.dp)
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

                    {

                        LazyColumn(   modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 10.dp),
                            contentPadding = PaddingValues(bottom = 100.dp),) {
                            items(filteredResult){result->
                                when(result){
                                    is SearchResult.User->{
                                        ListItem(
                                            headlineContent = {
                                                Row (verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.fillMaxWidth().padding(start = 10.dp)){
                                                    Image(painter = painterResource(id=result.imageResId), contentDescription ="User Image" ,
                                                        contentScale = ContentScale.Fit,
                                                        modifier=Modifier.size(50.dp)
                                                            .clip(CircleShape)
                                                    )
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Text(result.name)
                                                }
                                            },
                                            colors = ListItemDefaults.colors(
                                                containerColor = PrimaryColor,
                                                headlineColor = Color.Black,
                                            ),
                                            modifier=Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp).height(70.dp).clip(shape = RoundedCornerShape(15.dp)).clickable {
                                                navController.navigate("otherProfile/${result.name}/${result.imageResId}")
                                            }
                                        )
                                    }
                                    is SearchResult.Book->{
                                        ListItem(
                                            headlineContent = {
                                                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,)
                                                {
                                                    Image(painter = painterResource(id = result.imageResId), contentDescription = "Book Cover",

                                                        modifier = Modifier
                                                            .size(70.dp))
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Column(modifier = Modifier.weight(1f)) {
                                                        Text(result.title,)
                                                        Text(result.author,style= MaterialTheme.typography.bodySmall)
                                                    }
                                                    Text(result.rating, style = MaterialTheme.typography.bodySmall, color = Color.Red
                                                    )
                                                }
                                            },
                                            colors = ListItemDefaults.colors(
                                                containerColor = PrimaryColor,
                                                headlineColor = Color.Black,

                                                ), modifier=Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp).height(70.dp).clip(shape = RoundedCornerShape(15.dp)).clickable {
                                                navController.navigate("books/${result.id}/${result.title}/${result.author}/${result.imageResId}/${result.rating}")


                                            }
                                        )
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
                            CustomBox(modifier, title = "My Profile", imageResId = R.drawable.azad,navController = navController,"profile",onNavigate = { route ->
                                showBottomSheet = false
                                navController.navigate(route)
                            }, size = 80.dp)
                            CustomBox(modifier, "Top picks", imageResId = R.drawable.outline_person_24,navController = navController,"",onNavigate = { route ->
                                showBottomSheet = false
                                navController.navigate(route)
                            })
                            CustomBox(modifier, "Challenge", imageResId = R.drawable.outline_person_24,navController = navController,"challenge",onNavigate = { route ->
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
                    composable("profile") { ProfileScreen(navController) }
                    composable("search") { SearchScreen(navController) }
                    composable("myBooks") { MyBooksPage(navController) }
                    composable("booksIveRead") { BooksIveRead(viewModel = BooksViewModel(),navController=navController) }
                    composable("booksIWantToRead") { ToRead(viewModel = BooksViewModel(),navController=navController) }
                    composable("currentlyReading") { ReadingNow(viewModel = BooksViewModel(),navController=navController) }
                    composable("search_screen") { SearchScreen(navController = navController) }
                    composable("settings"){ SettingsScreen(navController) }
                    composable("groups") { GroupsPage(navController) }
                    composable("friends/{currentUser}") { backStackEntry ->
                        val currentUser = backStackEntry.arguments?.getString("currentUser") ?: ""
                        FriendsPage(navController = navController, currentUser = currentUser)
                    }
                    composable("challenge") { Challenge(navController) }
                    composable("medals") { MedalsPage(navController) }
                    composable("notifications"){ NotificationsScreen(navController) }
                    composable(
                        route = "books/{id}/{title}/{author}/{imageResId}/{rating}/{pageNumber}",
                        arguments = listOf(
                            navArgument("id") { type = NavType.StringType },
                            navArgument("title") { type = NavType.StringType },
                            navArgument("author") { type = NavType.StringType },
                            navArgument("imageResId") { type = NavType.IntType },
                            navArgument("rating") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id") ?: "-1"
                        val title = backStackEntry.arguments?.getString("title") ?: "Unknown Title"
                        val author = backStackEntry.arguments?.getString("author") ?: "Unknown Author"
                        val imageResId = backStackEntry.arguments?.getInt("imageResId") ?: R.drawable.farelerveinsanlar
                        val rating = backStackEntry.arguments?.getString("rating") ?: "no rating"
                        val pageNumber = backStackEntry.arguments?.getString("pageNumber")?.toIntOrNull() ?: -1
                        BooksScreen(navController, result = SearchResult.Book(id = id, title = title, author = author, imageResId = imageResId, rating = rating, pageNumber = pageNumber))
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
                        navArgument("userImageResId") { type = NavType.IntType }
                    )) { backStackEntry ->
                        val userName = backStackEntry.arguments?.getString("userName") ?: ""
                        val userImageResId = backStackEntry.arguments?.getInt("userImageResId") ?: R.drawable.loginimage
                        OtherProfilePage(userName = userName, userImageResId = userImageResId, navController = navController, currentUser = userName)
                    }

                }
            }

        })
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

