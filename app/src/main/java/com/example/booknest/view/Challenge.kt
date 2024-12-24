package com.example.booknest.view

import android.app.DatePickerDialog
import android.os.Build
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import com.example.booknest.Model.SearchResult
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


// Helper function to convert milliseconds to a readable date
fun convertMillisToDate(millis: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    return Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(formatter)
}
@Composable
fun Challenge(navController: NavController) {
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
                Text("Goruntule Books You've Read", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Featured Challenges",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Display added challenges with date range
            addedChallenges.value.forEachIndexed { index, challenge ->
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
                                text = challenge,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                            Text(
                                text = "Start: $startDate | End: $endDate",
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
        ChallengeBottomSheet(
            onChallengeAdded = { challengeTitle, numberInput, challengeType, dateRange ->
                val challengeDescription = if (challengeType == "book") {
                    "Read $numberInput books: $challengeTitle"
                } else {
                    "Read $numberInput pages: $challengeTitle"
                }

                // Add the challenge with title and date range
                addedChallenges.value = addedChallenges.value + challengeDescription
                challengeDates.value = challengeDates.value + Pair(challengeDescription, dateRange)

                showDialog.value = false
            },
            onDismiss = { showDialog.value = false }
        )
    }
}

// BottomSheet for adding a challenge
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeBottomSheet(
    onChallengeAdded: (String, String, String, Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var currentStep by remember { mutableStateOf(1) }
    val challengeName = remember { mutableStateOf("") }
    val bookOrPageChoice = remember { mutableStateOf("") }
    val numberInput = remember { mutableStateOf("") }
    val dateRangePickerState = rememberDateRangePickerState()

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
                Button(onClick = { currentStep = 2 }, modifier = Modifier.fillMaxWidth()) {
                    Text("Next")
                }
            }
        }

        // Step 2: Select type and number input
        if (currentStep == 2) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text("Select Challenge Type")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { bookOrPageChoice.value = "book" },
                        colors = ButtonDefaults.buttonColors(containerColor = if (bookOrPageChoice.value == "book") Color(0xFF3CB371) else Color.Gray)
                    ) {
                        Text("Books")
                    }

                    Button(
                        onClick = { bookOrPageChoice.value = "page" },
                        colors = ButtonDefaults.buttonColors(containerColor = if (bookOrPageChoice.value == "page") Color(0xFF3CB371) else Color.Gray)
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
                Button(onClick = { currentStep = 3 }, modifier = Modifier.fillMaxWidth()) {
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
                                onChallengeAdded(challengeName.value, numberInput.value, bookOrPageChoice.value, dateRange)
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
                        modifier = Modifier.fillMaxWidth().height(500.dp)
                    )
                }
            }
        }
    }
}
