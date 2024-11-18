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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
        NavItem("Profile", Icons.Default.Person),
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
    Scaffold (
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
                                1 -> navController.navigate("profile") // Profile sayfasına yönlendir
                                2 -> navController.navigate("search") // Notifications sayfasına yönlendir
                                3 -> navController.navigate("myBooks") // MyBooks sayfasına yönlendir
                                4 -> {

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
                    Column(modifier=Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly) {
                            CustomBox(modifier,"My Profile", imageResId = R.drawable.outline_person_24)
                            CustomBox(modifier,"Top picks",imageResId = R.drawable.outline_person_24)
                            CustomBox(modifier,"Challenge",imageResId = R.drawable.outline_person_24)
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly) {
                            CustomBox(modifier,"Friends",imageResId = R.drawable.outline_group_24)
                            CustomBox(modifier,"Groups",imageResId = R.drawable.sharp_groups_24)
                            CustomBox(modifier,"Giveaways",imageResId = R.drawable.outline_person_24)
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly) {
                            CustomBox(modifier,"Awards",imageResId = R.drawable.awards)
                            CustomBox(modifier,"Settings",imageResId = R.drawable.settings)
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
                composable("profile") { ProfileScreen() }
                composable("search") { SearchScreen(navController) }
                composable("myBooks") { MyBooksPage(navController) }
                composable("booksIveRead") { BooksIveRead(viewModel = BooksViewModel()) }
                composable("booksIWantToRead") { ToRead(viewModel = BooksViewModel()) }
                composable("currentlyReading") { ReadingNow(viewModel = BooksViewModel()) }
                composable("search_screen") { SearchScreen(navController = navController) }
                composable(
                    "otherProfile/{userName}/{userImageResId}",
                    arguments = listOf(
                        navArgument("userName") { type = NavType.StringType },
                        navArgument("userImageResId") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val userName = backStackEntry.arguments?.getString("userName") ?: ""
                    val userImageResId = backStackEntry.arguments?.getInt("userImageResId") ?: R.drawable.loginimage
                    OtherProfilePage(userName = userName, userImageResId = userImageResId)
                }
            }
            }

        })
    }



@Composable
fun CustomBox(modifier: Modifier = Modifier, title: String, imageResId: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .border(1.dp, Color.Gray, RoundedCornerShape(95.dp))
                .clip(RoundedCornerShape(95.dp))
                .size(80.dp)
                .clickable { }
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = title,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(60.dp)
                ,
            )
        }
        Text(
            text = title,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}


