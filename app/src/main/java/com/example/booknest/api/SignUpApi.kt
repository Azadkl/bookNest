package com.example.booknest.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpApi {

    @POST("api/Auth/signup/")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>
}
data class SignUpRequest(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val age: Int
)
data class SignUpResponse(
    val success: Boolean,
    val message: String?,
    val status: Int,
    val body: SignUpUser
)

data class SignUpUser(
    val id: Int,
    val username: String,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val email: String,
    val avatar: String,
    val createdAt: String
)

