package com.example.booknest.api

import com.example.booknest.api.Models.Achievement
import com.example.booknest.api.Models.Book
import com.example.booknest.api.Models.BookProgress
import com.example.booknest.api.Models.Challenge
import com.example.booknest.api.Models.Friend
import com.example.booknest.api.Models.FriendRequest
import com.example.booknest.api.Models.FriendResponse
import com.example.booknest.api.Models.MybooksList
import com.example.booknest.api.Models.Notification
import com.example.booknest.api.Models.NotificationResponse
import com.example.booknest.api.Models.OtherProfile
import com.example.booknest.api.Models.PostBook
import com.example.booknest.api.Models.PostBookProgress
import com.example.booknest.api.Models.ReceivedRequest
import com.example.booknest.api.Models.Review
import com.example.booknest.api.Models.Shelf
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("api/books/")
    suspend fun getBook(@Header("Authorization") accessToken: String): Response<GenelResponse<List<Book>>>

    @GET("api/books/{id}")
    suspend fun fetchOneBook(
        @Header("Authorization") accessToken: String,
        @Path("id") id: String
    ): Response<GenelResponse<Book>>

    @POST("api/books/")
    suspend fun postBook(
        @Header("Authorization") accessToken: String,
        @Body postBook: PostBook
    ): Response<GenelResponse<Book>>

    @POST("api/books/author/")
    suspend fun author(@Header("Authorization") refreshToken: String): Response<GenelResponse<Any>>

    @DELETE("api/Users/{id}")
    suspend fun deleteAccount(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<GenelResponse<Any>>

    @GET("api/challenges")
    suspend fun getChallenges(@Header("Authorization") accessToken: String): Response<GenelResponse<List<Challenge>>>

    @POST("api/challenges/")
    suspend fun postChallenge(
        @Header("Authorization") accessToken: String,
        @Body challenge: Challenge
    ):Response<GenelResponse<Challenge>>

    @PUT("api/challenges/{id}")
    suspend fun updateChallenge(
        @Header("Authorization") accessToken: String,
        @Path("id") challengeId: Int,
        @Body challenge: Challenge
    ): Response<GenelResponse<Challenge>>

    @POST("api/reviews/")
    suspend fun createReview(
        @Header("Authorization") accessToken: String,
        @Body review: Review
    ): Response<GenelResponse<Review>>

    @GET("api/reviews/user/")
    suspend fun getReviewsByUser(
        @Header("Authorization") accessToken: String
    ): Response<GenelResponse<List<Review>>>

    @GET("api/users/id/{id}")
    suspend fun getUserById(
        @Header("Authorization") accessToken: String,
        @Path ("id") id: Int
    ): Response<GenelResponse<OtherProfile>>


    @GET("api/reviews/book/{bookId}")
    suspend fun getReviewsByBook(
        @Header("Authorization") accessToken: String,
        @Path("bookId") bookId: String
    ): Response<GenelResponse<List<Review>>>

    @GET("api/reviews/{reviewId}")
    suspend fun getReviewById(
        @Header("Authorization") accessToken: String,
        @Path("reviewId") reviewId: Int
    ): Response<GenelResponse<Review>>

    // Send Friend Request
    @POST("api/friends")
    suspend fun sendFriendRequest(
        @Header("Authorization") accessToken: String,
        @Body friendRequest: FriendRequest
    ): Response<GenelResponse<FriendRequest>>

    // Respond to Friend Request
    @POST("api/friends/response")
    suspend fun respondToFriendRequest(
        @Header("Authorization") accessToken: String,
        @Body friendResponse: FriendResponse
    ): Response<GenelResponse<Friend>>


    // Get Sent Friend Requests
    @GET("api/friends/requests/sent")
    suspend fun getSentFriendRequests(
        @Header("Authorization") accessToken: String
    ): Response<GenelResponse<List<FriendRequest>>>

    // Get Received Friend Requests
    @GET("api/friends/requests/received")
    suspend fun getReceivedFriendRequests(
        @Header("Authorization") accessToken: String
    ): Response<GenelResponse<List<ReceivedRequest>>>

    @GET("api/notifications")
    suspend fun getNotifications(
        @Header("Authorization") accessToken: String
    ): Response<GenelResponse<List<NotificationResponse>>>


    @DELETE("api/bookprogress/{isbn}")
    suspend fun deleteBookProgress(
        @Header("Authorization") accessToken: String,
        @Path("isbn") isbn: String
    ): Response<GenelResponse<Any>>



    // Cancel Friend Request
    @DELETE("api/friends/requests/{friendId}")
    suspend fun cancelFriendRequest(
        @Header("Authorization") accessToken: String,
        @Path("friendId") friendId: Int
    ): Response<GenelResponse<Any>>

    // Remove Friend
    @DELETE("api/friends/{friendId}")
    suspend fun removeFriend(
        @Header("Authorization") accessToken: String,
        @Path("friendId") friendId: Int
    ): Response<GenelResponse<Any>>

    @POST("api/bookprogress")
    suspend fun postBookProgress(
        @Header("Authorization") accessToken: String,
        @Body postBookProgress: PostBookProgress
    ): Response<GenelResponse<BookProgress>>

    // Get Book Progress
    @GET("api/bookprogress")
    suspend fun getBookProgress(
        @Header("Authorization") accessToken: String
    ): Response<GenelResponse<MybooksList>>

    // Get Max Progress
    @GET("api/bookprogress/max")
    suspend fun getMaxProgress(
        @Header("Authorization") accessToken: String
    ): Response<GenelResponse<Int>>
    @POST("api/notifications")
    suspend fun createNotification(
        @Header("Authorization") accessToken: String,
        @Body notification: Notification
    ): Response<GenelResponse<Notification>>

    @GET("api/notifications/{id}")
    suspend fun getNotification(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int
    ): Response<GenelResponse<Notification>>








    @DELETE("api/notifications/{id}")
    suspend fun deleteNotification(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int
    ): Response<GenelResponse<Any>>

    @POST("api/achievements")
    suspend fun createAchievement(
        @Header("Authorization") accessToken: String,
        @Body achievement: Achievement
    ): Response<GenelResponse<Achievement>>

    @GET("api/achievements/{id}")
    suspend fun getAchievement(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int
    ): Response<GenelResponse<Achievement>>

    @GET("api/achievements/user")
    suspend fun getUserAchievements(
        @Header("Authorization") accessToken: String
    ): Response<GenelResponse<List<Achievement>>>

    @GET("api/achievements")
    suspend fun getAllAchievements(
        @Header("Authorization") accessToken: String
    ): Response<GenelResponse<List<Achievement>>>

    @POST("api/Shelf")
    suspend fun createShelf(
        @Header("Authorization") accessToken: String,
        @Body shelf: Shelf
    ): Response<GenelResponse<Shelf>>

    @GET("api/Shelf")
    suspend fun getShelves(
        @Header("Authorization") accessToken: String
    ): Response<GenelResponse<List<Shelf>>>

    @GET("api/Shelf/{id}")
    suspend fun getShelf(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int
    ): Response<GenelResponse<Shelf>>

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
