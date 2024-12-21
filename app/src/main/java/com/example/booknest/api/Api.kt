package com.example.booknest.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface BookNestApi {
    @POST("api/Auth/login/")
    suspend fun login(@Body request: LoginRequest): Response<GenelResponse<LoginBody>>

    @POST("api/Auth/logout/")
    suspend fun logout(@Header("Authorization") accessToken: String): Response<GenelResponse<Any>>

    @GET("api/Users/profile/")
    suspend fun profile(@Header("Authorization") accessToken: String): Response<GenelResponse<ProfileBody>>
    @GET("api/Users/")
    suspend fun users(@Header("Authorization") accessToken: String): Response<GenelResponse<List<ProfileBody>>>

    @POST("api/Auth/refreshtoken/")
    suspend fun refreshToken(@Header("Authorization") refreshToken: String): Response<GenelResponse<LoginBody>>

    @DELETE("api/Users/{id}")
    suspend fun deleteAccount(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<GenelResponse<Any>>

}


data class LoginRequest(val email: String, val password: String)
data class GenelResponse<T>(
    val success: Boolean,
    val message: String?,
    val status: Int,
    val body: T
)

data class LoginBody(
    val accessToken: String?,
    val refreshToken: String?,
    val userId: Int?,
    val user: ProfileBody? // Kullanıcı nesnesi için uygun tip kullanılabilir
)

data class ProfileBody(
    val id: Int,
    val username: String,
    val firstName: String?,
    val lastName: String?,
    val age: Int?,
    val email: String,
    val avatar: String,
    val bookUsers: Any?,
    val userAchievements: Any?,
    val userNotifications: Any?,
    val sentFriendRequests: Any?,
    val receivedFriendRequests: Any?,
    val groupRequests: Any?,
    val groupUsers: Any?,
    val friendsInitiated: Any?,
    val friendsReceived: Any?,
    val muted: Any?,
    val muter: Any?
)
