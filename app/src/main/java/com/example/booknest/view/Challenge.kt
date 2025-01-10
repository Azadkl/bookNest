package com.example.booknest.view

import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.booknest.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.booknest.Model.SearchResult
import com.example.booknest.ViewModel.LoginViewModel
import com.example.booknest.api.Models.Challenge
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

import java.util.*

// Helper function to convert milliseconds to a readable date
fun convertMillisToDate(millis: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    return Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(formatter)
}
@Composable
fun Challenge(navController: NavController, viewModel: LoginViewModel) {
    val challenge = viewModel.challenges
    val challengeTitle = "2024 Reading Challenge"
    val totalGoal = 50
    val booksRead = remember { 20 } // Example of state tracking
    val progress = booksRead / totalGoal.toFloat()

    // State to manage dialog visibility and challenge data
    val showDialog = remember { mutableStateOf(false) }
    val selectedChoice = remember { mutableStateOf("") }
    val numberInput = remember { mutableStateOf("") }
    val challengeTitleInput = remember { mutableStateOf("") }
    val addedChallenges = remember { mutableStateOf(
        listOf<String>()
    ) }
    val challengeDates = remember { mutableStateOf(
        listOf<Pair<String, Pair<Long?, Long?>>>() // List of pairs: (challengeName, dateRange)
    ) }
    LaunchedEffect (Unit){
        viewModel.getChallenges()
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        item {
            // Header Section
            Text(
                text = challengeTitle,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )

            // Add Challenge Button
            Button(
                onClick = { showDialog.value = true },
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CB371))
            ) {
                Text("Add Challenge", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("booksIveRead") },
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3CB371))
            ) {
                Text("Books You've Read", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Featured Challenges",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            // Display added challenges with date range
            challenge.value.forEachIndexed { index, challenge ->
                val dateRange = challengeDates.value.getOrNull(index)?.second
                val startDate = dateRange?.first?.let { convertMillisToDate(it) } ?: "N/A"
                val endDate = dateRange?.second?.let { convertMillisToDate(it) } ?: "N/A"

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFDFDFD))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.houseoflame), // Replace with actual drawable
                            contentDescription = "Challenge Icon",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                                Text(
                                    text = challenge.text,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )

                            Text(
                                text = "Start: ${challenge.startedAt} | End: ${challenge.endsAt}",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }

    // BottomSheet for challenge creation
    if (showDialog.value) {
        val challenge = viewModel.challenges
        ChallengeBottomSheet(
            onChallengeAdded = { challengeTitle, numberInput, challengeType, dateRange ->
                val challengeDescription = if (challengeType == "book") {
                    "Read $challenge books: $challengeTitle"
                } else {
                    "Read $numberInput pages: $challengeTitle"
                }

                // Add the challenge with title and date range
                addedChallenges.value = addedChallenges.value + challengeDescription
                challengeDates.value = challengeDates.value + Pair(challengeDescription, dateRange)

                showDialog.value = false
            },
            onDismiss = { showDialog.value = false },viewModel
        )
    }
}

// BottomSheet for adding a challenge


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeBottomSheet(

    onChallengeAdded: (String, String, String, Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit,
    viewModel: LoginViewModel
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var currentStep by remember { mutableStateOf(1) }
    val challengeName = remember { mutableStateOf("") }
    val bookOrPageChoice = remember { mutableStateOf("") }
    val numberInput = remember { mutableStateOf("") }
    val dateRangePickerState = rememberDateRangePickerState()
    var enable1 by remember { mutableStateOf(false) }
    var enable2 by remember { mutableStateOf(false) }
    val enable3 = false

    // DateTimeFormatter for the required format 'yyyy-MM-dd'T'HH:mm:ss'
    val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxHeight(0.6f),
        shape = RoundedCornerShape(0.dp)
    ) {
        // Step 1: Challenge title input
        if (currentStep == 1) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                TextField(
                    value = challengeName.value,
                    onValueChange = { challengeName.value = it },
                    label = { Text("Challenge Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                enable1 = challengeName.value.isNotEmpty()
                Button(
                    enabled = enable1,
                    onClick = { currentStep = 2 },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF877f6f))
                ) {
                    Text("Next")
                }
            }
        }

        // Step 2: Select type and number input
        if (currentStep == 2) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Select Challenge Type")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { bookOrPageChoice.value = "book" },
                        colors = ButtonDefaults.buttonColors(containerColor = if (bookOrPageChoice.value == "book") Color(0xFF5f4d3f) else Color.Gray)
                    ) {
                        Text("Books")
                    }
                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = { bookOrPageChoice.value = "page" },
                        colors = ButtonDefaults.buttonColors(containerColor = if (bookOrPageChoice.value == "page") Color(0xFF5f4d3f) else Color.Gray)
                    ) {
                        Text("Pages")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = numberInput.value,
                    onValueChange = { numberInput.value = it },
                    label = { Text("Number (of books/pages)") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                enable2 = (bookOrPageChoice.value.isNotEmpty() && numberInput.value.isNotEmpty())
                Button(
                    onClick = { currentStep = 3 },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enable2,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF877f6f))
                ) {
                    Text("Next")
                }
            }
        }

        // Step 3: Date picker
        if (currentStep == 3) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                DatePickerDialog(
                    onDismissRequest = onDismiss,
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val dateRange = Pair(
                                    dateRangePickerState.selectedStartDateMillis,
                                    dateRangePickerState.selectedEndDateMillis
                                )
                                // Ensure to pass null values if not selected
                                onChallengeAdded(challengeName.value, numberInput.value, bookOrPageChoice.value, dateRange)

                                // Convert dates to the required format
                                val startMillis = dateRange.first ?: 0L
                                val endMillis = dateRange.second ?: 0L

                                // Convert startMillis and endMillis to the required date format
                                val startDate = Instant.ofEpochMilli(startMillis)
                                    .atZone(ZoneId.systemDefault())
                                    .format(dateTimeFormatter)
                                val endDate = Instant.ofEpochMilli(endMillis)
                                    .atZone(ZoneId.systemDefault())
                                    .format(dateTimeFormatter)

                                val newChallenge = Challenge(
                                    isCompleted = false,
                                    objective = numberInput.value.toInt(),
                                    startedAt = startDate,  // sending formatted string
                                    text = challengeName.value,
                                    type = bookOrPageChoice.value,
                                    endsAt = endDate  // sending formatted string
                                )
                                Log.d("dates", "$newChallenge")
                                viewModel.postChallenge(newChallenge)
                                onDismiss()
                            }
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = onDismiss) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DateRangePicker(
                        state = dateRangePickerState,
                        title = { Text("Select date range") },
                        showModeToggle = false,
                        modifier = Modifier.fillMaxWidth().height(500.dp).padding(15.dp)
                    )
                }
            }
        }
    }
}
