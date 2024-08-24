package it.unical.informatica.ea.sefora_frontend.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.unical.informatica.ea.sefora_frontend.activity.performRegistration
import it.unical.informatica.ea.sefora_frontend.apis.UserControllerApi
import it.unical.informatica.ea.sefora_frontend.infrastructure.ClientException
import it.unical.informatica.ea.sefora_frontend.infrastructure.ServerException
import it.unical.informatica.ea.sefora_frontend.models.AuthenticationRequest
import it.unical.informatica.ea.sefora_frontend.models.RegisterRequest
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.regex.Pattern

class LoginViewModel : ViewModel() {

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

    val _userControllerApi = UserControllerApi()

    fun onEmailChanged(newEmail: String) {
        email = newEmail
        emailError = if (!isValidEmail(newEmail)) "Invalid email format" else null
    }

    fun onPasswordChanged(newPassword: String) {
        password = newPassword
        passwordError = if (!isValidPassword(newPassword)) "Password must match requirements" else null
        // Controlla anche la corrispondenza con la conferma password
        confirmPasswordError = if (newPassword != confirmPassword) "Passwords do not match" else null
    }

    fun onFirstNameChanged(newFirstName: String) {
        firstName = newFirstName
        firstNameError = if (!isValidFirstName(newFirstName)) "Invalid name" else null
    }

    fun onLastNameChanged(newLastName: String) {
        lastName = newLastName
        lastNameError = if (!isValidLastName(newLastName)) "Invalid surname" else null
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
        // Clear any previous error
        error = null

        viewModelScope.launch {
            try {
                if (emailError == null && passwordError == null) {
                    _userControllerApi.authenticate(
                        AuthenticationRequest(
                            email = email,
                            password = password
                        )
                    )
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
                    _userControllerApi.register(
                        RegisterRequest(
                            email = email,
                            password = password,
                            firstname = firstName,
                            lastname = lastName,
                            role = RegisterRequest.Role.USER
                        )
                    )
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
                email = ""
                firstName = ""
                lastName = ""
            }
        }
    }

    // Helper function for email validation
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
        return emailRegex.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        // Definisci i requisiti di validità della password
        val passwordRegex = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$"
        )
        return passwordRegex.matcher(password).matches()
    }

    private fun isValidFirstName(firstName: String): Boolean {
        // Definisci i requisiti di validità del nome
        val firstNameRegex = Pattern.compile("^[a-zA-Z\\s]+$") // Solo lettere e spazi
        return firstNameRegex.matcher(firstName).matches() && firstName.isNotEmpty() // Non vuoto
    }

    private fun isValidLastName(lastName: String): Boolean {
        // Definisci i requisiti di validità del cognome
        val lastNameRegex = Pattern.compile("^[a-zA-Z\\s]+$") // Solo lettere e spazi
        return lastNameRegex.matcher(lastName).matches() && lastName.isNotEmpty() // Non vuoto
    }

}
