package com.example.booknest.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.booknest.R
import com.example.booknest.ViewModel.LoginViewModel
import com.example.booknest.ui.theme.PrimaryColor

@Composable
fun SettingsScreen(mainNavController: NavController,viewModel: LoginViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        item { HeaderText() }
        item { ProfileCardUI(viewModel) }
        item { GeneralOptionsUI(mainNavController = mainNavController, viewModel) }
        item { SupportOptionsUI() }
    }
}


@Composable
fun HeaderText() {
    Text(
        text = "Settings",

        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 10.dp),

        fontSize = 36.sp
    )
}

@Composable
fun ProfileCardUI(viewModel: LoginViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(
                    text = "Edit Your Profile",

                    fontSize = 16.sp,

                )

                viewModel.profileResponse.value?.email?.let {  Text(
                    text =it,

                    color = Color.Gray,
                    fontSize = 15.sp,

                )}

                Button(
                    modifier = Modifier.padding(top = 10.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                       containerColor = PrimaryColor
                    ),
                    contentPadding = PaddingValues(horizontal = 30.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 2.dp
                    ),
                    shape = CircleShape
                ) {
                    Text(
                        text = "View",
                        color = Color.Black,
                        fontSize = 12.sp,

                    )
                }
            }
            Image(
                painter = rememberImagePainter(viewModel.profileResponse.value?.avatar),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(90.dp)
                    .clip(shape = CircleShape)
            )
        }
    }
}


@Composable
fun GeneralOptionsUI(mainNavController: NavController,viewModel: LoginViewModel) {
    var deleteAccountDialog by remember { mutableStateOf(false) } // kontrol
    var showLogoutDialog by remember { mutableStateOf(false) } // Dialog kontrolü için state

    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        Text(
            text = "General",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        GeneralSettingItem(
            imageVector = Icons.Default.Settings,
            mainText = "Notifications",
            subText = "Customize notifications",
            onClick = {} // Boş tıklama işlevi
        )
        DarkModeSettingItem()
        GeneralSettingItem(
            imageVector = Icons.Default.ExitToApp,
            mainText = "Sign out",
            subText = "Log out of the application",
            onClick = { showLogoutDialog = true } // Dialogu aç
        )

        // Çıkış Onay Dialogu
        if (showLogoutDialog) {
            LogoutDialog(
                onConfirm = {
                    // Login ekranına git
                    viewModel.logout()
                    mainNavController.navigate("SignIn") {
                        popUpTo("SignIn") { inclusive = true }
                    }
                },
                onDismiss = { showLogoutDialog = false }
            )
        }
        GeneralSettingItem(
            imageVector = Icons.Default.ExitToApp,
            mainText = "Delete Account",
            subText = "Permanently delete your account",
            onClick = { deleteAccountDialog = true }
        )

        if (deleteAccountDialog) {
            DeletionDialog(
                onConfirm = {
                    viewModel.deleteAccount(
                        token = viewModel.accessToken.value ?: "",  // Ensure you pass a valid token

                        onSuccess = {
                            mainNavController.navigate("login") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        onFailure = { errorMessage ->
                            Log.e("DeleteAccount", errorMessage)
                        }
                    )
                },
                onDismiss = { deleteAccountDialog = false }
            )
        }

    }
}
@Composable
fun DeletionDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(16.dp), // Rounded corners for the dialog
        title = {
            Text(
                text = "Delete the Account?",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp) // Space below title
            )
        },
        text = {
            Text(
                text = "Are you sure you want to delete your account? This action cannot be undone.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp) // Space below text
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E8B57),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text("Yes, Delete")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF2E8B57)
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text("Cancel")
            }
            }
        )
}
@Composable
fun LogoutDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = { Text("Sign Out") },
        text = { Text("Are you sure you want to sign out?") },
        confirmButton = {
            Button(onClick = {
                onConfirm()
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E8B57)
                )) {
                Text("Yes")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E8B57)
                )) {
                Text("No")
            }
        }
    )
}


@Composable
fun GeneralSettingItem(imageVector: ImageVector, mainText: String, subText: String, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = CircleShape)

                ) {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))
                Column(
                    modifier = Modifier.offset(y = (2).dp)
                ) {
                    Text(
                        text = mainText,
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = subText,
                        color = Color.Gray,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.offset(y = (-4).dp)
                    )
                }
            }
            Icon(
                imageVector = Icons.Filled.ArrowForwardIos,
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )

        }
    }
}

@Composable
fun SupportOptionsUI() {
    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        Text(
            text = "Support",
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        SupportItem(
            imageVector = Icons.Default.Contacts,
            mainText = "Contact",
            onClick = {}
        )
        SupportItem(
            imageVector = Icons.Default.Feedback,
            mainText = "Feedback",
            onClick = {}
        )
        SupportItem(
            imageVector = Icons.Default.PrivacyTip,
            mainText = "Privacy Policy",
            onClick = {}
        )
    }
}


@Composable
fun SupportItem(imageVector: ImageVector, mainText: String, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = CircleShape)
                        .background(Color.White)
                ) {
                    Icon(
                        imageVector=imageVector,
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = mainText,
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Icon(
                imageVector = Icons.Filled.ArrowForwardIos,
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )

        }
    }
}
@Composable
fun DarkModeSettingItem() {
    var isDarkModeEnabled by remember { mutableStateOf(false) }

    Card(
        onClick = { isDarkModeEnabled = !isDarkModeEnabled },
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = CircleShape)
                        .background(Color.LightGray)
                ) {
                    Icon(
                        imageVector = Icons.Default.DarkMode,
                        contentDescription = "Dark Mode Icon",
                        tint = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = "Dark Mode",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Switch(
                checked = isDarkModeEnabled,
                onCheckedChange = { isDarkModeEnabled = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Black,
                    uncheckedThumbColor = Color.Gray
                )
            )
        }
    }
}
