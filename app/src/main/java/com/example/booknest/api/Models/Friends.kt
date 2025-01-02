package com.example.booknest.api.Models

data class FriendRequest(val friendId: Int)
data class FriendResponse(val senderId: Int, val response: Boolean)

data class Friend(val friendId: Int, val name: String)
