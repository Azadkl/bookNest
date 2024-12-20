package com.example.booknest.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.outlined.Numbers
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booknest.R
import com.example.booknest.ViewModel.SignUpViewModel
import com.example.booknest.ui.theme.ButtonColor1
import com.example.booknest.ui.theme.ButtonColor2

@SuppressLint("UnrememberedMutableState")
@Composable
fun SignUpScreen(navController: NavController,viewModel: SignUpViewModel) {
    Box (modifier = Modifier.fillMaxSize()) {
        val image: Painter = painterResource(id = R.drawable.loginimage)
        val _shortPasswordError = mutableStateOf(false)
        val shortPasswordError: State<Boolean> = _shortPasswordError
        Image(
            painter = image,
            contentDescription = "My Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 50.dp),
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
            val usernameFocusRequester = remember { FocusRequester() }
            val emailFocusRequester = remember { FocusRequester() }
            val ageFocusRequester = remember { FocusRequester() }
            val passwordFocusRequester = remember { FocusRequester() }
            var username by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var age by remember { mutableIntStateOf(0) }
            var password by remember { mutableStateOf("") }
            var passwordVisible by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 25.dp)
                    .clip(RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp))
                    .background(ButtonColor1),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Create Account", style = TextStyle(
                        fontSize = 25.sp
                    ), modifier = Modifier.padding(top = 30.dp)
                )
                OutlinedTextField(
                    value = username,
                    placeholder = { Text("First and last name") },
                    onValueChange = { username = it },
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .size(width = 355.dp, height = 55.dp)
                        .border(width = 0.dp, color = Color.Transparent)
                        .focusRequester(usernameFocusRequester),
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
                            emailFocusRequester.requestFocus()
                        }
                    )
                )
                OutlinedTextField(
                    value = email,
                    placeholder = { Text("Your email addres") },
                    onValueChange = { email = it },
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .size(width = 355.dp, height = 55.dp)
                        .border(width = 0.dp, color = Color.Transparent)
                        .focusRequester(emailFocusRequester),
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Mail,
                            contentDescription = null
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            ageFocusRequester.requestFocus()
                        }
                    )
                )
                OutlinedTextField(
                    value = age.toString(),  // Convert integer to string for display
                    placeholder = { Text("Your age") },
                    onValueChange = {
                        // Ensure the input is numeric and update the state
                        if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                            age = it.toIntOrNull() ?: 0
                        }
                    },
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .size(width = 355.dp, height = 55.dp)
                        .border(width = 0.dp, color = Color.Transparent)
                        .focusRequester(ageFocusRequester),
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Numbers,
                            contentDescription = null
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
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
                    onValueChange = { password = it },
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .size(width = 355.dp, height = 55.dp)
                        .border(width = 0.dp, color = Color.Transparent)
                        .focusRequester(passwordFocusRequester),
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
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    )
                )
//               if (shortPasswordError.value) {
//                   Row(
//                       verticalAlignment = Alignment.CenterVertically,
//                       modifier = Modifier.padding(16.dp)
//                   ) {
//                       Icon(
//                           imageVector = Icons.Filled.ErrorOutline,
//                           contentDescription = "Error",
//                       )
//                       Spacer(modifier = Modifier.width(8.dp))
//
//                             viewModel.setErrorMessage("şifre 6")
//
//
//
//                   }
//               }

                if (viewModel.errorMessage.value.isNotEmpty()) {
                    Text(
                        text = viewModel.errorMessage.value,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
                Button(
                    onClick = {
                    if (username.isEmpty() || password.isEmpty() ||email.isEmpty() || age.toString().isEmpty()) {
                        viewModel.setErrorMessage("Please fill in all fields")
                        return@Button
                    }
                        else if (password.length<7 ) {
                        viewModel.setErrorMessage("şifre 6")
                            return@Button
                        }
                        else{_shortPasswordError.value=false
                            viewModel.signUp(username, firstName = "", lastName = "", email, password,age)}



                    }, modifier = Modifier
                        .padding(top = 10.dp)
                        .size(width = 280.dp, height = 40.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(ButtonColor2)
                ) {
                    if (viewModel.isLoading.value) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                    } else {
                        Text("Create Account", style = TextStyle(fontSize = 20.sp))
                    }
                }

                // Sign-In button
                Spacer(modifier = Modifier.padding(15.dp))
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
                Button(
                    onClick = {
                        navController.navigate("SignIn")
                    },
                    modifier = Modifier.padding(top = 10.dp).size(width = 280.dp, height = 40.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(ButtonColor2)

                ) {
                    Text(
                        "Sign-In now", style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                }
            }
        }
    }
}
