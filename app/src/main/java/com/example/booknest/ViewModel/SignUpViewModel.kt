package com.example.booknest.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import com.example.booknest.api.SignUpApi
import com.example.booknest.api.SignUpRequest
import com.example.booknest.api.apiSignUp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel : ViewModel() {

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isSignedUp = mutableStateOf(false)
    val isSignedUp: State<Boolean> = _isSignedUp



    // Sign Up işlemi
    fun signUp(username: String, firstName: String, lastName: String, email: String, password: String, age: Int) {
        _isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiSignUp.signUp(SignUpRequest(username, firstName, lastName, email, password, age))

                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val signUpResponse = response.body()
                        if (signUpResponse?.success == true) {
                            _isSignedUp.value = true
                            _errorMessage.value = "Sign Up Successful!"
                        } else {
                            _errorMessage.value = signUpResponse?.message ?: "Sign up failed"
                        }
                    } else {
                        _errorMessage.value = "Sign up failed: ${response.body()?.success}"
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _isLoading.value = false
                    _errorMessage.value = "An error occurred: ${e.message}"
                }
            }
        }
    }

    fun setErrorMessage(message: String) {
        _errorMessage.value = message
    }
}
