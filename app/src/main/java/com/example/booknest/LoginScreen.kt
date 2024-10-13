package com.example.booknest

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.booknest.ui.theme.ButtonColor1
import com.example.booknest.ui.theme.ButtonColor2
import com.example.booknest.ui.theme.Pink40
import com.example.booknest.ui.theme.PrimaryColor

@Composable
fun LoginScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()){
        val image: Painter = painterResource(id = R.drawable.loginimage)
        Image(
            painter = image,
            contentDescription = "My Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row (modifier = Modifier.fillMaxWidth().padding(bottom = 80.dp),
                horizontalArrangement = Arrangement.Center){
                Text("book", style = TextStyle(
                    fontSize = 70.sp
                )
                )
                Text("Nest", style = TextStyle(
                    fontWeight =FontWeight.Bold,
                    fontSize = 70.sp))
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 100.dp)) {
                Button(onClick = {
                    navController.navigate("SignIn")
                },
                    modifier = Modifier.size(width = 350.dp, height = 40.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(ButtonColor2)

                ) {
                    Text("Sign in", style = TextStyle(
                        fontSize = 20.sp))
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Button(onClick = {
                    navController.navigate("SignUp")
                },
                    modifier = Modifier.size(width = 350.dp, height = 40.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors =ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = ButtonColor1
                    )
                ) {
                    Text("Sign up", style = TextStyle(
                        fontSize = 20.sp
                    ))
                }
//                Image(
//                    painter = painterResource(R.drawable.loginimage2),
//                    contentDescription = null)
            }

        }
    }
}



