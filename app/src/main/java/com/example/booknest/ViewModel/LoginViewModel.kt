package com.example.booknest.ViewModel
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booknest.api.GenelResponse
import com.example.booknest.api.LoginRequest
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
    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    private val _accessToken = mutableStateOf<String?>(null)
    val accessToken: State<String?> = _accessToken

    private val _refreshToken = mutableStateOf<String?>(null)
    val refreshToken: State<String?> = _refreshToken

    private val _profileResponse = mutableStateOf<ProfileBody?>(null)
    val profileResponse: State<ProfileBody?> = _profileResponse
    private val _usersResponse = mutableStateOf<List<ProfileBody>?>(null)
    val usersResponse: State<List<ProfileBody>?> = _usersResponse

    private val _deleteAccountResponse = mutableStateOf<String?>(null)
    val deleteAccountResponse: State<String?> = _deleteAccountResponse


    fun setTokens(accessToken: String, refreshToken: String?) {
        _accessToken.value = accessToken
        _refreshToken.value = refreshToken
    }

    // Kullanıcı girişi başarılı mı?
    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn: State<Boolean> = _isLoggedIn
    // Loading durumu
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading
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
                            _accessToken.value = accessToken
                            _refreshToken.value = refreshToken
                            _isLoggedIn.value = true
                            _errorMessage.value = "Sign-out Successful"
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
                            }
                        } else {
                            _errorMessage.value = "Refresh token failed: ${response.message()}"
                            onFailure()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "An error occurred during refresh: ${e.message}"
                        onFailure()
                    }
                }
            }
        } else {
            _errorMessage.value = "No refresh token available. Please login again."
            onFailure()
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
                        } else if (response.code() == 401) {
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
                        }  else {
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
                        if (response.isSuccessful) {
                            // Logout başarılı, local tokenları temizle
                            _accessToken.value = null
                            _refreshToken.value = null
                            _isLoggedIn.value = false
                            Log.d("LoginViewModel", "Logout successful")
                        } else {
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
}

