package com.example.booknest.ViewModel
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.booknest.api.GenelResponse
import com.example.booknest.api.LoginRequest
import com.example.booknest.api.Models.Achievement
import com.example.booknest.api.Models.Book
import com.example.booknest.api.Models.BookProgress
import com.example.booknest.api.Models.Challenge
import com.example.booknest.api.Models.Friend
import com.example.booknest.api.Models.FriendRequest
import com.example.booknest.api.Models.FriendResponse
import com.example.booknest.api.Models.MybooksList
import com.example.booknest.api.Models.Notification
import com.example.booknest.api.Models.PostBook
import com.example.booknest.api.Models.PostBookProgress
import com.example.booknest.api.Models.Review
import com.example.booknest.api.Models.Shelf
import com.example.booknest.api.ProfileBody
import com.example.booknest.api.api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _accessToken = mutableStateOf<String?>(null)
    val accessToken: State<String?> = _accessToken

    private val _refreshToken = mutableStateOf<String?>(null)
    val refreshToken: State<String?> = _refreshToken

    private val _profileResponse = mutableStateOf<ProfileBody?>(null)
    val profileResponse: State<ProfileBody?> = _profileResponse

    private val _bookResponse = mutableStateOf<List<Book>?>(null)
    val bookResponse: State<List<Book>?> = _bookResponse

    private val _usersResponse = mutableStateOf<List<ProfileBody>?>(null)
    val usersResponse: State<List<ProfileBody>?> = _usersResponse

    private val _deleteAccountResponse = mutableStateOf<String?>(null)
    val deleteAccountResponse: State<String?> = _deleteAccountResponse

    private val _challenges = mutableStateOf<List<Challenge>>(emptyList())
    val challenges: State<List<Challenge>> = _challenges

    private val _reviews = mutableStateOf<List<Review>>(emptyList())
    val reviews: State<List<Review>> = _reviews

    private val _friendRequestsSent = mutableStateOf<List<FriendRequest>>(emptyList())
    val friendRequestsSent: State<List<FriendRequest>> = _friendRequestsSent

    private val _friendRequestsReceived = mutableStateOf<List<FriendRequest>>(emptyList())
    val friendRequestsReceived: State<List<FriendRequest>> = _friendRequestsReceived

    private val _friends = mutableStateOf<List<Friend>>(emptyList())
    val friends: State<List<Friend>> = _friends

    private val _myBooks = mutableStateOf<MybooksList?>(null)
    val myBooks: State<MybooksList?> = _myBooks

    private val _bookProgress = mutableStateOf<BookProgress?>(null)
    val bookProgress: State<BookProgress?> = _bookProgress

    private val _maxProgress = mutableStateOf<Int?>(null)
    val maxProgress: State<Int?> = _maxProgress

    private val _notificationResponse = mutableStateOf<Notification?>(null)
    val notificationResponse: State<Notification?> = _notificationResponse

    private val _notificationsResponse = mutableStateOf<List<Notification>?>(null)
    val notificationsResponse: State<List<Notification>?> = _notificationsResponse

    private val _achievementResponse = mutableStateOf<Achievement?>(null)
    val achievementResponse: State<Achievement?> = _achievementResponse

    private val _achievementsResponse = mutableStateOf<List<Achievement>?>(null)
    val achievementsResponse: State<List<Achievement>?> = _achievementsResponse

    private val _shelfResponse = mutableStateOf<Shelf?>(null)
    val shelfResponse: State<Shelf?> = _shelfResponse

    private val _shelvesResponse = mutableStateOf<List<Shelf>?>(null)
    val shelvesResponse: State<List<Shelf>?> = _shelvesResponse

    fun setTokens(accessToken: String, refreshToken: String?) {
        _accessToken.value = accessToken
        _refreshToken.value = refreshToken
    }

    // Kullanıcı girişi başarılı mı?
    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn: State<Boolean> = _isLoggedIn
    // Loading durumu
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    // Login işlemi gerçekleştiren fonksiyon
    fun login(username: String, password: String) {
        _isLoading.value = true  // Login işlemi başlatıldı
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.login(LoginRequest(username, password))

                viewModelScope.launch(Dispatchers.Main) {
                    _isLoading.value=false
                    if (response.isSuccessful) {
                        val accessToken = response.body()?.body?.accessToken
                        val refreshToken=response.body()?.body?.refreshToken
                        if (accessToken != null && refreshToken != null) {
                            setTokens(accessToken,refreshToken)
                            _isLoggedIn.value = true
                            Log.d("LoginViewModel", "Login Successful - AccessToken: $accessToken")
                        } else {
                            _isLoggedIn.value = false // Başarısız giriş
                            _errorMessage.value = "Incorrect e-mail or password"
                        }
                    } else {
                        _isLoggedIn.value = false // Başarısız giriş
                        _errorMessage.value = "Login failed: ${response.message()}"
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _isLoading.value=false
                    _isLoggedIn.value = false // Başarısız giriş
                    _errorMessage.value = "An error occurred: ${e.message}"
                }
            }
        }
    }
    /**
     * Access Token yenileme işlemi
     */
    fun refreshToken(onSuccess: () -> Unit, onFailure: () -> Unit) {
        val currentRefreshToken = _refreshToken.value
        if (currentRefreshToken != null) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.refreshToken("Bearer $currentRefreshToken")
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            val newAccessToken = response.body()?.body?.accessToken
                            if (newAccessToken != null) {
                                _accessToken.value = newAccessToken
                                onSuccess()
                            } else {
                                _errorMessage.value = "Failed to refresh token: No access token received."
                                onFailure()
                                logout()
                            }
                        } else {
                            _errorMessage.value = "Refresh token failed: ${response.message()}"
                            onFailure()
                            logout()

                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred during refresh: ${e.message}"
                        onFailure()
                        logout()

                    }
                }
            }
        } else {
            _errorMessage.value = "No refresh token available. Please login again."
            onFailure()
            logout()

        }
    }
    /**
     * Profil bilgilerini alma
     */
    fun fetchProfile() {
        val token = _accessToken.value
        if (token != null) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.profile("Bearer $token")
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            Log.d("Raw Response", response.raw().toString())
                            Log.d("Response Body", response.body()?.toString() ?: "Response body is null")
                            Log.d("LoginViewModel", "Profile fetch response: ${response.body()}")

                            response.body()?.body?.let { profile ->
                                _profileResponse.value = profile
                            } ?: run {
                                _errorMessage.value = "Profile response body is null."
                            }
                        } else if (response.body()?.success == false) {
                            // Token süresi dolmuşsa yenileme yap
                            refreshToken(
                                onSuccess = { fetchProfile() },
                                onFailure = { logout() }
                            )
                        } else {
                            _errorMessage.value = "Failed to fetch profile: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }
    fun fetchUsers() {
        val token = _accessToken.value
        if (token != null) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.users("Bearer $token")
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            Log.d("Raw Response", response.raw().toString())
                            Log.d("Response Body", response.body()?.toString() ?: "Response body is null")
                            Log.d("LoginViewModel", "Users fetch response: ${response.body()}")

                            response.body()?.body?.let { users ->
                                _usersResponse.value = users
                            } ?: run {
                                _errorMessage.value = "Users response body is null."
                            }
                        }
                        else if (response.body()?.success == false) {
                            // Token süresi dolmuşsa yenileme yap
                            refreshToken(
                                onSuccess = { fetchUsers() },
                                onFailure = { logout() }
                            )
                        } else {
                            _errorMessage.value = "Failed to fetch users: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    /**
     * Logout işlemi
     */
    fun logout() {
        val currentAccessToken = _accessToken.value
        if (currentAccessToken != null) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    // Backend'e logout isteği gönder
                    val response = api.logout("Bearer $currentAccessToken")

                    withContext(Dispatchers.Main) {
                        if (response.body()?.success ?: false) {
                            // Logout başarılı, local tokenları temizle
                            _accessToken.value = null
                            _refreshToken.value = null
                            _isLoggedIn.value = false
                            Log.d("LoginViewModel", "Logout successful")
                        } else if (response.body()?.success == false) {
                            // Token süresi dolmuşsa yenileme yap
                            refreshToken(
                                onSuccess = {
                                    // Yenilenen access token ile logout yap
                                    logout()
                                },
                                onFailure = {
                                    // Refresh token da başarısızsa çıkış yap
                                    _accessToken.value = null
                                    _refreshToken.value = null
                                    _isLoggedIn.value = false
                                    Log.e("LoginViewModel", "Token expired and refresh failed, logged out")
                                }
                            )
                        }  else {
                            _errorMessage.value = "Logout failed: ${response.message()}"
                            Log.e("LoginViewModel", "Logout failed: ${response.message()}")
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred during logout: ${e.message}"
                        Log.e("LoginViewModel", "Error during logout: ${e.message}")
                    }
                }
            }
        } else {
            // Local token yoksa hemen temizle
            _accessToken.value = null
            _refreshToken.value = null
            _isLoggedIn.value = false
            Log.d("LoginViewModel", "No access token, logged out locally.")
        }
    }

    fun deleteAccount(
        token: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        val formattedToken = token.replace("Bearer ", "")

        Log.d("DeleteAccount", "Formatted Token: $formattedToken")


        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = api.profile("Bearer $formattedToken")  // This is in the background thread
                withContext(Dispatchers.Main) {  // Ensure UI updates happen on the main thread
                    if (response.isSuccessful) {
                        response.body()?.body?.let { profile ->
                            val userId = profile.id

                            // Proceed to delete account using the retrieved userId
                            val deleteResponse = api.deleteAccount("Bearer $formattedToken", userId.toString())
                            if (deleteResponse.isSuccessful) {
                                _deleteAccountResponse.value = "Account deleted successfully"

                                Log.d("LoginViewModel", "Account delete successful")
                                onSuccess()

                                _accessToken.value = null
                                _refreshToken.value = null
                                _isLoggedIn.value = false
                                _errorMessage.value = "deletion successful"
                            } else {
                                _deleteAccountResponse.value = "Failed to delete account: ${deleteResponse.message()}"
                                onFailure("Failed to delete account: ${deleteResponse.message()}")
                            }
                        } ?: run {
                            _deleteAccountResponse.value = "Profile response body is null."
                            onFailure("Profile response body is null.")
                        }
                    } else {
                        _deleteAccountResponse.value =
                            "Failed to fetch user profile. Server response: ${response.code()}"
                        onFailure("Failed to fetch user profile. Server response: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {  // Ensure error handling happens on the main thread
                    _deleteAccountResponse.value = "Error: ${e.message}"
                    onFailure("Error: ${e.message}")
                }
            }
        }
    }

    // Hata mesajını değiştiren bir fonksiyon
    fun setErrorMessage(message: String) {
        _errorMessage.value = message
    }
        fun fetchBook() {
            val token = _accessToken.value
            if (token != null) {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val response = api.getBook("Bearer $token")
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                Log.d("Raw Response", response.raw().toString())
                                Log.d("Response Body", response.body()?.toString() ?: "Response body is null")
                                Log.d("LoginViewModel", "Book fetch response: ${response.body()}")

                                response.body()?.body?.let { book ->
                                    _bookResponse.value = book
                                } ?: run {
                                    _errorMessage.value = "Book response body is null."
                                }
                            } else if (response.body()?.success == false) {
                                // Token süresi dolmuşsa yenileme yap
                                refreshToken(
                                    onSuccess = { fetchBook() },
                                    onFailure = { logout() }
                                )
                            } else {
                                _errorMessage.value = "Failed to fetch book: ${response.message()}"
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            _errorMessage.value = "An error occurred: ${e.message}"
                        }
                    }
                }
            } else {
                _errorMessage.value = "Access token is null. Please login again."
            }
        }
    // BookProgress verisini backend'e göndermek için fonksiyon
    fun postBook( isbn: String)  {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            _errorMessage.value = "" // Hata mesajını sıfırla
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    // Creating the request body with parameters
                    val postBook = PostBook(isbn)
                    Log.d("postBookProgress","$postBook")
                    // Making the API call
                    val response = api.postBook("Bearer $token", postBook)
                    Log.d("giris fonksiyon postbook","$response")
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            var status = response.body()?.status
                            Log.d("basarili response successful","$response")
                            _errorMessage.value = "Book successfully added!"
                        } else {
                            Log.d("donen response body'si", "${response.body()}")
                            _errorMessage.value =  "Failed to add book: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }
    // Get Challenges
    fun getChallenges() {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.getChallenges("Bearer $token")
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _challenges.value = response.body()?.body ?: emptyList()
                        } else {
                            _errorMessage.value = "Failed to fetch challenges: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // Post Challenge
    fun postChallenge(challenge: Challenge) {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.postChallenge("Bearer $token", challenge)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _challenges.value = _challenges.value + (response.body()?.body ?: challenge)
                        } else {
                            _errorMessage.value = "Failed to post challenge: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // Update Challenge
    fun updateChallenge(challengeId: Int, challenge: Challenge) {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.updateChallenge("Bearer $token", challengeId, challenge)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _challenges.value = _challenges.value.map {
                                if (it == challenge) response.body()?.body ?: it else it
                            }
                        } else {
                            _errorMessage.value = "Failed to update challenge: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // Create Review
    fun createReview(review: Review) {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.createReview("Bearer $token", review)
                    Log.d("APIResponse", "Response body: ${response.body()}")
                    val responseBody = response.body()?.body ?: review
                    Log.d("APIResponse", "Response body: $responseBody")
                    Log.d("APIResponse", "Raw response: ${response.raw()}")


                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _reviews.value += (response.body()?.body ?: review)
                        } else {
                            _errorMessage.value = "Failed to create review: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // Get Reviews by User
    fun getReviewsByUser() {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.getReviewsByUser("Bearer $token")
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _reviews.value = response.body()?.body ?: emptyList()
                        } else {
                            _errorMessage.value = "Failed to fetch user reviews: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // Get Reviews by Book
    fun getReviewsByBook(bookId: String) {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.getReviewsByBook("Bearer $token", bookId)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _reviews.value = response.body()?.body ?: emptyList()
                        } else {
                            _errorMessage.value = "Failed to fetch reviews for book: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // Get Review by ID
    fun getReviewById(reviewId: Int) {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.getReviewById("Bearer $token", reviewId)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _reviews.value = listOf(response.body()?.body ?: return@withContext)
                        } else {
                            _errorMessage.value = "Failed to fetch review: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // Send Friend Request
    fun sendFriendRequest(friendId: Int) {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.sendFriendRequest("Bearer $token", FriendRequest(friendId))
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _friendRequestsSent.value = _friendRequestsSent.value + (response.body()?.body ?: return@withContext)
                        } else {
                            _errorMessage.value = "Failed to send friend request: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // Respond to Friend Request
    fun respondToFriendRequest(senderId: Int, response: Boolean) {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val responseBody = FriendResponse(senderId, response)
                    val apiResponse = api.respondToFriendRequest("Bearer $token", responseBody)
                    withContext(Dispatchers.Main) {
                        if (apiResponse.isSuccessful) {
                            _friends.value = _friends.value + (apiResponse.body()?.body ?: return@withContext)
                        } else {
                            _errorMessage.value = "Failed to respond to friend request: ${apiResponse.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // Get Sent Friend Requests
    fun getSentFriendRequests() {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.getSentFriendRequests("Bearer $token")
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _friendRequestsSent.value = response.body()?.body ?: emptyList()
                        } else {
                            _errorMessage.value = "Failed to fetch sent requests: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // Get Received Friend Requests
    fun getReceivedFriendRequests() {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.getReceivedFriendRequests("Bearer $token")
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _friendRequestsReceived.value = response.body()?.body ?: emptyList()
                        } else {
                            _errorMessage.value = "Failed to fetch received requests: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // Cancel Friend Request
    fun cancelFriendRequest(friendId: Int) {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.cancelFriendRequest("Bearer $token", friendId)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _friendRequestsSent.value = _friendRequestsSent.value.filter { it.friendId != friendId }
                        } else {
                            _errorMessage.value = "Failed to cancel request: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // Remove Friend
    fun removeFriend(friendId: Int) {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.removeFriend("Bearer $token", friendId)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _friends.value = _friends.value.filter { it.friendId != friendId }
                        } else {
                            _errorMessage.value = "Failed to remove friend: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // BookProgress verilerini almak için fonksiyon
    fun getBookProgress() {
        val token = _accessToken.value
        Log.d("isloggedin:","$_isLoggedIn")
        Log.d("if ten onceki token", "$token")
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.getBookProgress("Bearer $token")
                    Log.d("APIResponse", "Response body: ${response.body()}")
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            response.body()?.body?.let { body ->
                                _myBooks.value = _myBooks.value?.copy(
                                    read = body.read ?: emptyList(),
                                    reading = body.reading ?: emptyList(),
                                    wantToRead = body.wantToRead ?: emptyList()
                                ) ?: MybooksList(
                                    read = body.read ?: emptyList(),
                                    reading = body.reading ?: emptyList(),
                                    wantToRead = body.wantToRead ?: emptyList()
                                )
                            }
                        } else {
                            _errorMessage.value = "Failed to get book progress: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
            Log.d("if ten sonraki meası", "$_errorMessage")
        }
    }

    // BookProgress verisini backend'e göndermek için fonksiyon
    fun postBookProgress( bookId: String, status: String, progress: Int)  {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    // Creating the request body with parameters
                    val postBookProgress = PostBookProgress(bookId, status, progress)
                    Log.d("postBookProgress","$postBookProgress")
                    // Making the API call
                    val response = api.postBookProgress("Bearer $token", postBookProgress)
                    Log.d("giris fonksiyon postbook","$response")
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            var status = response.body()?.status
                            Log.d("basarili response successful","$response")
                        } else {
                            Log.d("donen response body'si", "${response.body()}")
                            _errorMessage.value = "Failed to create review: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // Maksimum ilerleme verisini almak için fonksiyon
    fun getMaxProgress() {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.getMaxProgress("Bearer $token")
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _maxProgress.value = response.body()?.body
                        } else {
                            _errorMessage.value = "Failed to get max progress: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // POST: Yeni bildirim oluşturma
    fun createNotification(notification: Notification) {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.createNotification("Bearer $token", notification)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _notificationResponse.value = response.body()?.body
                        } else {
                            _errorMessage.value = "Failed to create notification: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // GET: Belirli bir bildirim al
    fun getNotification(id: Int) {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.getNotification("Bearer $token", id)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _notificationResponse.value = response.body()?.body
                        } else {
                            _errorMessage.value = "Failed to fetch notification: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // DELETE: Belirli bir bildirimi sil
    fun deleteNotification(id: Int) {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.deleteNotification("Bearer $token", id)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _errorMessage.value = "Notification deleted successfully."
                        } else {
                            _errorMessage.value = "Failed to delete notification: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // POST: Yeni başarı oluşturma
    fun createAchievement(achievement: Achievement) {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.createAchievement("Bearer $token", achievement)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _achievementResponse.value = response.body()?.body
                        } else {
                            _errorMessage.value = "Failed to create achievement: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // GET: Belirli bir başarıyı al
    fun getAchievement(id: Int) {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.getAchievement("Bearer $token", id)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _achievementResponse.value = response.body()?.body
                        } else {
                            _errorMessage.value = "Failed to fetch achievement: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // GET: Kullanıcının tüm başarılarını al
    fun getUserAchievements() {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.getUserAchievements("Bearer $token")
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _achievementsResponse.value = response.body()?.body
                        } else {
                            _errorMessage.value = "Failed to fetch user achievements: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // GET: Tüm başarıları al
    fun getAllAchievements() {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.getAllAchievements("Bearer $token")
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _achievementsResponse.value = response.body()?.body
                        } else {
                            _errorMessage.value = "Failed to fetch all achievements: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // POST: Yeni kitaplık oluşturma
    fun createShelf(shelf: Shelf) {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.createShelf("Bearer $token", shelf)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _shelfResponse.value = response.body()?.body
                        } else {
                            _errorMessage.value = "Failed to create shelf: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // GET: Tüm kitaplıkları al
    fun getShelves() {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.getShelves("Bearer $token")
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _shelvesResponse.value = response.body()?.body
                        } else {
                            _errorMessage.value = "Failed to fetch shelves: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

    // GET: Belirli bir kitaplığı al
    fun getShelf(id: Int) {
        val token = _accessToken.value
        if (token != null) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.getShelf("Bearer $token", id)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            _shelfResponse.value = response.body()?.body
                        } else {
                            _errorMessage.value = "Failed to fetch shelf: ${response.message()}"
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred: ${e.message}"
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        } else {
            _errorMessage.value = "Access token is null. Please login again."
        }
    }

}

