package com.example.booknest.api.Models

data class OtherProfile(
    val avatar: String,
    val id: Int,
    val isFriend: Boolean,
    val pendingReceivedRequest: Boolean,
    val pendingSentRequest: Boolean,
    val read: List<BookProgress>,
    val reading: List<BookProgress>,
    val username: String,
    val wantToRead: List<BookProgress>
)