package com.example.booknest.api.Models

data class Challenge(
    val isCompleted: Boolean,
    val objective: Int,
    val startedAt: String,
    val text: String,
    val type: String,
    val endsAt: String,
)