package it.unical.informatica.ea.sefora_frontend.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.unical.informatica.ea.sefora_frontend.apis.AccountControllerApi
import it.unical.informatica.ea.sefora_frontend.auth.TokenManager
import it.unical.informatica.ea.sefora_frontend.infrastructure.ClientException
import it.unical.informatica.ea.sefora_frontend.infrastructure.ServerException
import it.unical.informatica.ea.sefora_frontend.models.AccountDto
import it.unical.informatica.ea.sefora_frontend.models.AuthenticationRequest
import it.unical.informatica.ea.sefora_frontend.models.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class LoginViewModel @Inject constructor(
    val tokenManager: TokenManager,
) : ViewModel() {

    var email by mutableStateOf("")
        private set
    var emailError by mutableStateOf<String?>(null)

    var password by mutableStateOf("")
        private set
    var passwordError by mutableStateOf<String?>(null)

    var confirmPassword by mutableStateOf("")
        private set
    var confirmPasswordError by mutableStateOf<String?>(null)

    var firstName by mutableStateOf("")
        private set
    var firstNameError by mutableStateOf<String?>(null)

    var lastName by mutableStateOf("")
        private set
    var lastNameError by mutableStateOf<String?>(null)

    var isLoginMode by mutableStateOf(true)
        private set
    var error by mutableStateOf<String?>(null)

    private val _accountControllerApi = AccountControllerApi()
    private val _isLoggedIn = MutableStateFlow(false)
    val alreadyLoggedIn: StateFlow<Boolean> = _isLoggedIn
    val account = MutableStateFlow<AccountDto?>(null)

    init {
        viewModelScope.launch {
            try {
                if (tokenManager.getAccessToken() != null) {
                    Log.w("LoginViewModel", "Token is not null, getting current user")
                    getCurrentUser()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoggedIn.value = tokenManager.isAccessTokenExpired().not()
                if (!_isLoggedIn.value) {
                    tokenManager.clearTokens()
                }
            }
        }
    }

    fun onEmailChanged(newEmail: String) {
        email = newEmail
        emailError = if (!AccountDto.validateEmail(newEmail)) "Invalid email format" else null
    }

    fun onPasswordChanged(newPassword: String) {
        password = newPassword
        passwordError = if (!AccountDto.validatePassword(newPassword)) "Password must match requirements" else null
        // Controlla anche la corrispondenza con la conferma password
        confirmPasswordError = if (newPassword != confirmPassword) "Passwords do not match" else null
    }

    fun onFirstNameChanged(newFirstName: String) {
        firstName = newFirstName
        firstNameError = if (!AccountDto.validateFirstName(newFirstName)) "Invalid name" else null
    }

    fun onLastNameChanged(newLastName: String) {
        lastName = newLastName
        lastNameError = if (!AccountDto.validateLastName(newLastName)) "Invalid last name" else null
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        confirmPassword = newConfirmPassword
        confirmPasswordError = if (password != confirmPassword) "Passwords do not match" else null
    }

    // Mode Toggle
    fun toggleMode() {
        isLoginMode = !isLoginMode
        // Clear errors when switching modes
        emailError = null
        passwordError = null
        firstNameError = null
        lastNameError = null
        error = null
    }

    // Authentication/Registration Logic
    fun login(onSuccess: () -> Unit) {
        error = null
        viewModelScope.launch {
            try {
                if (emailError == null && passwordError == null) {
                    val response = _accountControllerApi.authenticate(
                        AuthenticationRequest(
                            email = email,
                            password = password
                        )
                    )
                    tokenManager.saveTokens(
                        accessToken = response.accessToken,
                        refreshToken = response.refreshToken,
                        account = response.account,
                        expires_in = response.expiresIn
                    )
                    account.value = response.account
                    _isLoggedIn.value = true
                    onSuccess()
                } else {
                    error = "Invalid email or password format"
                }
            } catch (e: ClientException) {
                e.printStackTrace()
                error = "Invalid email or password"
            } catch (e: ServerException) {
                e.printStackTrace()
                error = "Server error, please try again later"
            } catch (e: IOException) {
                e.printStackTrace()
                error = "Network error, please check your connection"
            } catch (e: Exception) {
                e.printStackTrace()
                error = "An unexpected error occurred"
            } finally {
                password = ""
                email = ""
            }
        }
    }

    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                if (emailError == null && passwordError == null && firstNameError == null && lastNameError == null) {
                    val response = _accountControllerApi.register(
                        RegisterRequest(
                            email = email,
                            password = password,
                            firstname = firstName,
                            lastname = lastName,
                            role = RegisterRequest.Role.USER
                        )
                    )
                    tokenManager.saveTokens(
                        accessToken = response.accessToken,
                        refreshToken = response.refreshToken,
                        account = response.account,
                        expires_in = response.expiresIn
                    )
                    account.value = response.account
                    _isLoggedIn.value = true
                    onSuccess()
                }
            } catch (e: ClientException) {
                e.printStackTrace()
                error = "Invalid email or password"
            } catch (e: ServerException) {
                e.printStackTrace()
                error = "Server error, please try again later"
            } catch (e: IOException) {
                e.printStackTrace()
                error = "Network error, please check your connection"
            } catch (e: Exception) {
                e.printStackTrace()
                error = "An unexpected error occurred"
            } finally {
                password = ""
                confirmPassword = ""
                email = ""
                firstName = ""
                lastName = ""
            }
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            try {
                val response = _accountControllerApi.getConnectedAccount(tokenManager.getAccessToken()!!)
                tokenManager.saveTokens(
                    accessToken = tokenManager.getAccessToken()!!,
                    refreshToken = tokenManager.getRefreshToken()!!,
                    account = response,
                    expires_in = tokenManager.getTimeUntilTokenExpires()
                )
                account.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                Log.w("LoginViewModel", "Something happened, forcing user to log in anyway.")
                tokenManager.clearTokens()
            } finally {
                _isLoggedIn.value = tokenManager.isAccessTokenExpired().not()
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _accountControllerApi.logout(tokenManager.getAccessToken()!!)
            tokenManager.clearTokens()
            _isLoggedIn.value = false
        }
    }
}