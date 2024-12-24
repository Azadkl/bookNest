package com.example.booknest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.booknest.ViewModel.LoginViewModel
import com.example.booknest.ViewModel.SignUpViewModel
import com.example.booknest.ui.theme.BookNestTheme
import com.example.booknest.ui.theme.PrimaryColor
import com.example.booknest.view.BooksIveRead
import com.example.booknest.view.BottomBarScreen
import com.example.booknest.view.LoginScreen
import com.example.booknest.view.MyBooksPage
import com.example.booknest.view.ReadingNow
import com.example.booknest.view.SignInScreen
import com.example.booknest.view.SignUpScreen
import com.example.booknest.view.ToRead

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookNestTheme() {

                Scaffold(modifier = Modifier.fillMaxSize()
                ) {contentPadding->
                    Surface(modifier = Modifier.fillMaxSize().padding(contentPadding),
                        color = PrimaryColor,
                    ) {
                            NavigationExample()
                    }
                }
            }
        }
    }
}


@Composable
fun NavigationExample(){
    val navController = rememberNavController()
    val viewModel=LoginViewModel()
    NavHost(navController = navController, startDestination = "login") {
        composable("login"){ LoginScreen(navController,viewModel) }
        composable("SignIn"){ SignInScreen(navController, viewModel = viewModel) }
        composable("SignUp"){ SignUpScreen(navController, viewModel = SignUpViewModel()) }
        composable("home"){ BottomBarScreen(mainNavController=navController, viewModel = viewModel) }
    }
}