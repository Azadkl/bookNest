package com.example.booknest

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booknest.ui.theme.ButtonColor1
import com.example.booknest.ui.theme.ButtonColor2
import com.example.booknest.ui.theme.PrimaryColor

@Composable
fun SignUpScreen(navController: NavController) {
    Column (
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Row (modifier = Modifier.fillMaxWidth().padding(top = 50.dp),
            horizontalArrangement = Arrangement.Center,
        ){
            Text("book", style = TextStyle(
                fontSize = 70.sp
            )
            )
            Text("Nest", style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 70.sp)
            )

        }
        val focusManager = LocalFocusManager.current
        val usernameFocusRequester = remember { FocusRequester() }
        val emailFocusRequester = remember { FocusRequester() }
        val passwordFocusRequester = remember { FocusRequester() }
        var username by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(top = 25.dp)
            .clip(RoundedCornerShape(topStart = 80.dp, topEnd = 80.dp))
            .background(ButtonColor1),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text("Create Account", style = TextStyle(
                fontSize = 25.sp
            ), modifier = Modifier.padding(top = 30.dp))
            OutlinedTextField(
                value = username,
                placeholder = { Text("First and last name") },
                onValueChange = {username = it},
                modifier = Modifier
                    .padding(top = 50.dp)
                    .size(width = 355.dp,height=55.dp)
                    .border(width =0.dp, color = Color.Transparent )
                    .focusRequester(usernameFocusRequester),
                shape= RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                leadingIcon = { Icon(imageVector = Icons.Outlined.Person, contentDescription = null) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        emailFocusRequester.requestFocus()
                    }
                )
            )
            OutlinedTextField(
                value = email,
                placeholder = { Text("Your email addres") },
                onValueChange = {email = it},
                modifier = Modifier
                    .padding(top = 30.dp)
                    .size(width = 355.dp,height=55.dp)
                    .border(width =0.dp, color = Color.Transparent )
                    .focusRequester(emailFocusRequester),
                shape= RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                leadingIcon = { Icon(imageVector = Icons.Outlined.Mail, contentDescription = null) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        passwordFocusRequester.requestFocus()
                    }
                )
            )
            OutlinedTextField(
                value = password,
                placeholder = { Text("Booknest password") },
                onValueChange = {password = it},
                modifier = Modifier
                    .padding(top = 30.dp)
                    .size(width = 355.dp,height=55.dp)
                    .border(width =0.dp, color = Color.Transparent )
                    .focusRequester(passwordFocusRequester)
                ,
                shape= RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                leadingIcon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = null) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    IconButton(onClick = {passwordVisible = !passwordVisible}) {
                        Icon(imageVector = icon, contentDescription = null)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ErrorOutline,
                    contentDescription = "Error",
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Passwords must be at least 6 characters.",

                )

            }
            Button(onClick = {},
                modifier = Modifier.padding(top = 40.dp).size(width = 280.dp, height = 40.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(ButtonColor2)

            ) {
                Text("Create account", style = TextStyle(
                    fontSize = 20.sp))
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,

            ) {
                Divider(
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Already have an account?",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Divider(
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
            }
            Button(onClick = {
               navController.navigate("SignIn")
            },
                modifier = Modifier.padding(top = 10.dp).size(width = 280.dp, height = 40.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(ButtonColor2)

            ) {
                Text("Sign-In now", style = TextStyle(
                    fontSize = 20.sp))
            }
        }
    }
}