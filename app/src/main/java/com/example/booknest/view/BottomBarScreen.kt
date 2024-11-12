package com.example.booknest.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.vector.DefaultTintColor
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.booknest.NavItem
import com.example.booknest.ui.theme.ButtonColor1
import com.example.booknest.ui.theme.PrimaryColor

@Composable
fun BottomBarScreen(navController: NavController,modifier: Modifier=Modifier) {
    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Profile", Icons.Default.Person),
        NavItem("Notificatio", Icons.Default.Notifications),
        NavItem("MyBooks", Icons.Default.Book),
        NavItem("Settings", Icons.Default.Settings),

    )
    val contentPadding = PaddingValues(0.dp)
    var navController = rememberNavController()
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
                                2 -> navController.navigate("notifications") // Notifications sayfasına yönlendir
                                3 -> navController.navigate("myBooks") // MyBooks sayfasına yönlendir
                                4 -> navController.navigate("settings") // Settings sayfasına yönlendir
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
                composable("notifications") { NotificationsScreen() }
                composable("myBooks") { MyBooksPage() }
                composable("settings") { SettingsScreen() }
            }

        }
    }
    )
}
@Composable
fun ContentScreen(modifier: Modifier=Modifier,selectedIndex : Int) {
    when (selectedIndex) {
        0-> HomePageScreen()
        1 -> ProfileScreen()
        2 -> NotificationsScreen()
        3 -> MyBooksPage()
        4 -> SettingsScreen()
    }
}
