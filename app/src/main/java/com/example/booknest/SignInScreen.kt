package com.example.booknest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booknest.ui.theme.ButtonColor1
import com.example.booknest.ui.theme.ButtonColor2

@Composable
fun SignInScreen(navController: NavController) {
    Box (modifier = Modifier.fillMaxSize()){
        val image: Painter = painterResource(id = R.drawable.loginimage)
        Image(
            painter = image,
            contentDescription = "My Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 100.dp, bottom = 150.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    "book", style = TextStyle(
                        fontSize = 70.sp
                    )
                )
                Text(
                    "Nest", style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 70.sp
                    )
                )

            }
            val focusManager = LocalFocusManager.current
            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var passwordVisible by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier.fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 80.dp, topEnd = 80.dp))
                    .background(ButtonColor2),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    "Sign in", style = TextStyle(
                        fontSize = 40.sp
                    ),
                    modifier = Modifier.padding(top = 30.dp)
                )
                OutlinedTextField(
                    value = username,
                    placeholder = { Text("E-mail") },
                    onValueChange = { username = it },
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .size(width = 355.dp, height = 55.dp)
                        .border(width = 0.dp, color = Color.Transparent)
                        .focusable(),
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )
                OutlinedTextField(
                    value = password,
                    placeholder = { Text("Booknest password") },
                    onValueChange = { password = it },
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .size(width = 355.dp, height = 55.dp)
                        .border(width = 0.dp, color = Color.Transparent),
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = null
                        )
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon =
                            if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = icon, contentDescription = null)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                )
                Button(
                    onClick = {
                        navController.navigate("HomePage")
                    },
                    modifier = Modifier.padding(top = 40.dp).size(width = 280.dp, height = 40.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(ButtonColor1)

                ) {
                    Text(
                        "Sign in", style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                }
                Text("Forgot your password ?", style = TextStyle(
                    fontSize = 20.sp
                ),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .clickable { })

            }


        }
    }
}