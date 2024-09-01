package it.unical.informatica.ea.sefora_frontend.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.JsonEncodingException
import dagger.hilt.android.lifecycle.HiltViewModel
import it.unical.informatica.ea.sefora_frontend.apis.AccountControllerApi
import it.unical.informatica.ea.sefora_frontend.apis.WishlistControllerApi
import it.unical.informatica.ea.sefora_frontend.auth.TokenManager
import it.unical.informatica.ea.sefora_frontend.infrastructure.ClientException
import it.unical.informatica.ea.sefora_frontend.infrastructure.ServerException
import it.unical.informatica.ea.sefora_frontend.models.AccountDto
import it.unical.informatica.ea.sefora_frontend.models.AccountShortDto
import it.unical.informatica.ea.sefora_frontend.models.ChangePasswordRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

data class AccountState(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var oldPassword: String = "",
    var newPassword: String = "",
    var confirmPassword: String = "",
    var phone: String = "",
    val role : AccountDto.Role = AccountDto.Role.USER,
    val profilePic : Bitmap? = null,
)

data class AdminState(
    val accounts: List<AccountShortDto> = emptyList(),
)

@HiltViewModel
class AccountViewModel @Inject constructor(
    val tokenManager: TokenManager,
): ViewModel() {
    private val accountApi = AccountControllerApi()
    private val wishlistApi = WishlistControllerApi()
    private val _accountUpd = MutableStateFlow(AccountState())
    val accountState: StateFlow<AccountState> = _accountUpd.asStateFlow()
    private val _account = MutableStateFlow<AccountDto?>(null)
    private val _adminState = MutableStateFlow(AdminState())
    val adminState: StateFlow<AdminState> = _adminState.asStateFlow()
    val account: StateFlow<AccountDto?> = _account.asStateFlow()
    val isLoading = mutableStateOf(false)

    var firstNameError by mutableStateOf<String?>(null)
    var lastNameError by mutableStateOf<String?>(null)
    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)
    var phoneError by mutableStateOf<String?>(null)
    var profilePicError by mutableStateOf<String?>(null)
    var oldPasswordError by mutableStateOf<String?>(null)
    var newPasswordError by mutableStateOf<String?>(null)
    var confirmPasswordError by mutableStateOf<String?>(null)
    var error by mutableStateOf<String?>(null)

    fun onFirstNameChanged(firstName: String) {
        _accountUpd.value = _accountUpd.value.copy(firstName = firstName)
        firstNameError = if(!AccountDto.validateFirstName(_accountUpd.value.firstName)) "First name cannot be empty" else null
    }

    fun onLastNameChanged(lastName: String) {
        _accountUpd.value = _accountUpd.value.copy(lastName = lastName)
        lastNameError = if(!AccountDto.validateLastName(_accountUpd.value.lastName)) "Last name cannot be empty" else null
    }

    fun onEmailChanged(email: String) {
        _accountUpd.value = _accountUpd.value.copy(email = email)
        emailError = if(!AccountDto.validateEmail(_accountUpd.value.email)) "Invalid email" else null
    }

    fun onPhoneChanged(phone: String) {
        _accountUpd.value = _accountUpd.value.copy(phone = phone)
        phoneError = if(!AccountDto.validatePhone(_accountUpd.value.phone)) "Invalid phone number" else null
    }

    fun onProfilePicChanged(profilePic: Bitmap?) {
        _accountUpd.value = _accountUpd.value.copy(profilePic = profilePic)
    }

    fun onOldPasswordChanged(oldPassword: String) {
        _accountUpd.value = _accountUpd.value.copy(oldPassword = oldPassword)
        oldPasswordError = if(!AccountDto.validatePassword(_accountUpd.value.oldPassword)) "Password must be at least 8 characters long" else null
    }

    fun onNewPasswordChanged(newPassword: String) {
        _accountUpd.value = _accountUpd.value.copy(newPassword = newPassword)
        newPasswordError = if(!AccountDto.validatePassword(_accountUpd.value.newPassword)) "Password must be at least 8 characters long" else null
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _accountUpd.value = _accountUpd.value.copy(confirmPassword = confirmPassword)
        confirmPasswordError = if(_accountUpd.value.newPassword != _accountUpd.value.confirmPassword) "Passwords do not match" else null
    }

    // TODO implementare logica del token e del login
    fun updateAccount(
        id: Long,
        role: AccountDto.Role,
        banned: Boolean,
        image: Bitmap?,
        onSuccess: () -> Unit
    ) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val account = AccountDto(
                    id = id,
                    firstname = _accountUpd.value.firstName,
                    lastname = _accountUpd.value.lastName,
                    email = _accountUpd.value.email,
                    phone = _accountUpd.value.phone,
                    role = role,
                    profileImage = image,
                    banned = banned
                )
                accountApi.updateAccount(account, tokenManager.getAccessToken()!!)
                onSuccess()
            } catch (e: ClientException) {
                e.printStackTrace()
                error = "Invalid data"
            } catch (e: ServerException) {
                e.printStackTrace()
                error = "Server error, please try again later"
            } catch (e: IOException) {
                e.printStackTrace()
                error = "Network error, please check your connection"
            } catch (e: Exception) {
                e.printStackTrace()
                error = "An unexpected error occurred"
            }
        }.invokeOnCompletion {
            isLoading.value = false
        }
    }

    fun deleteAccount(id: Long, onSuccess: () -> Unit) {
        isLoading.value = true
        viewModelScope.launch {
            accountApi.deleteAccount(id, tokenManager.getAccessToken()!!)
        }.invokeOnCompletion {
            isLoading.value = false
            onSuccess()
        }
    }

    fun makeAdmin(id: Long, onSuccess: () -> Unit) {
        isLoading.value = true
        viewModelScope.launch {
            accountApi.makeAdmin(id, tokenManager.getAccessToken()!!)
        }.invokeOnCompletion {
            isLoading.value = false
            onSuccess()
        }
    }

    fun revokeAdmin(id: Long, onSuccess: () -> Unit) {
        isLoading.value = true
        viewModelScope.launch {
            accountApi.removeAdmin(id, tokenManager.getAccessToken()!!)
        }.invokeOnCompletion {
            isLoading.value = false
            onSuccess()
        }
    }

    fun banAccount(id: Long, onSuccess: () -> Unit) {
        isLoading.value = true
        viewModelScope.launch {
            accountApi.banAccount(id, tokenManager.getAccessToken()!!)
        }.invokeOnCompletion {
            isLoading.value = false
            onSuccess()
        }
    }

    fun unbanAccount(id: Long, onSuccess: () -> Unit) {
        isLoading.value = true
        viewModelScope.launch {
            accountApi.unbanAccount(id, tokenManager.getAccessToken()!!)
        }.invokeOnCompletion {
            isLoading.value = false
            onSuccess()
        }
    }

    fun getById(id: Long) {
        isLoading.value = true
        viewModelScope.launch {
            val account = accountApi.getAccountById(id)
            _account.value = account
        }.invokeOnCompletion {
            isLoading.value = false
        }
    }

    fun updatePassword(oldPassword: String, newPassword: String, confirmPassword: String, onSuccess: () -> Unit) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                accountApi.changePassword(
                    ChangePasswordRequest(
                        currentPassword = oldPassword,
                        newPassword = newPassword,
                        confirmationPassword = confirmPassword
                    ),
                    tokenManager.getAccessToken()!!
                )
                onSuccess()
            } catch(e: JsonEncodingException) {
                Log.w("Moshi", "Fuck Moshi :)")
                onSuccess()
            } catch (e: ClientException) {
                e.printStackTrace()
                error = "Invalid data"
            } catch (e: ServerException) {
                e.printStackTrace()
                error = "Server error, please try again later"
            } catch (e: IOException) {
                e.printStackTrace()
                error = "Network error, please check your connection"
            } catch (e: Exception) {
                e.printStackTrace()
                error = "An unexpected error occurred"
            }
        }.invokeOnCompletion {
            isLoading.value = false
        }
    }

    fun updateImage(image: Bitmap, id: Long, email: String, firstName: String, onSuccess: () -> Unit) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                accountApi.updateImage(
                    AccountShortDto(
                        id = id,
                        email = email,
                        firstname = firstName,
                        profileImage = image
                ), tokenManager.getAccessToken()!!)
                onSuccess()
            } catch(e: JsonEncodingException) {
                Log.w("Moshi", "Fuck Moshi :)")
                onSuccess()
            } catch (e: ClientException) {
                e.printStackTrace()
                error = "Invalid data"
            } catch (e: ServerException) {
                e.printStackTrace()
                error = "Server error, please try again later"
            } catch (e: IOException) {
                e.printStackTrace()
                error = "Network error, please check your connection"
            } catch (e: Exception) {
                e.printStackTrace()
                error = "An unexpected error occurred"
            }
        }.invokeOnCompletion {
            isLoading.value = false
        }
    }

    fun getAllAccounts() {
        isLoading.value = true
        viewModelScope.launch {
            val list = accountApi.getAllAccounts(tokenManager.getAccessToken()!!)
            _adminState.value = _adminState.value.copy(
                accounts = list
            )
        }.invokeOnCompletion {
            isLoading.value = false
        }
    }
}