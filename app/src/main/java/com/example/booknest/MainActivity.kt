package com.example.booknest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.booknest.ui.theme.BookNestTheme
import com.example.booknest.ui.theme.Pink40
import com.example.booknest.ui.theme.PrimaryColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookNestTheme {

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
    NavHost(navController = navController, startDestination = "home") {
        composable("home"){ LoginScreen(navController) }
        composable("SignIn"){ SignInScreen() }
        composable("SignUp"){ SignUpScreen(navController) }
    }
}