package com.example.booknest.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BookNestApi {
    @POST("api/Auth/login/") // Burada sonlandırma '/' gereksiz değil
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}


data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String)

