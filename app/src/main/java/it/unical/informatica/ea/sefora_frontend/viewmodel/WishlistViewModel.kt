package it.unical.informatica.ea.sefora_frontend.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.unical.informatica.ea.sefora_frontend.apis.WishlistControllerApi
import it.unical.informatica.ea.sefora_frontend.auth.TokenManager
import it.unical.informatica.ea.sefora_frontend.infrastructure.ClientException
import it.unical.informatica.ea.sefora_frontend.infrastructure.ServerException
import it.unical.informatica.ea.sefora_frontend.models.AccountDto
import it.unical.informatica.ea.sefora_frontend.models.WishlistDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

data class WishlistState(
    val wishlists: List<WishlistDto> = emptyList()
)

data class AddUserState(
    val email : String = "",
)

@HiltViewModel
class WishlistViewModel @Inject constructor(
    var tokenManager: TokenManager,
) : ViewModel(){
    private val wishlistApi = WishlistControllerApi()
    private val _wishlistState = MutableStateFlow(WishlistState())
    val wishlistState: StateFlow<WishlistState> = _wishlistState.asStateFlow()
    private val _wishlist= MutableStateFlow<List<WishlistDto?>>(emptyList())
    val wishlists: StateFlow<List<WishlistDto?>> = _wishlist.asStateFlow()
    val isLoading = MutableStateFlow(false)
    val currentAccountId = MutableStateFlow<Long?>(null)
    val isLoggedIn = mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    private val _addUserState = MutableStateFlow(AddUserState())
    var addUserState : StateFlow<AddUserState> = _addUserState.asStateFlow()
    var emailError by mutableStateOf<String?>(null)


    fun getLoggedInValue(): Boolean {
        return tokenManager.getAccessToken() == null
    }

    fun getWishlistByCurrentUser() {
        isLoading.value = true
        viewModelScope.launch {
            try {
                if (tokenManager.getAccessToken() == null) {
                    error = "Access token is null"
                    return@launch
                }

                val wishlist = wishlistApi.getAllAccessibleWishlists(tokenManager.getAccessToken()!!)
                _wishlistState.value = _wishlistState.value.copy(
                    wishlists = wishlist
                )

                if (tokenManager.getAccount() != null) {
                    currentAccountId.value = tokenManager.getAccount()!!.id
                } else {
                    error = "Account is null"
                }
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
            } finally {
                isLoading.value = false
            }
        }
    }

    fun addProductToWishlist(productId: Long, onSuccess: () -> Unit) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                wishlistApi.addProductToWishlist(productId, tokenManager.getAccessToken()!!)
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
            } finally {
                isLoading.value = false
            }
        }
    }

    fun removeProductFromWishlist(productId: Long, onSuccess: () -> Unit) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                wishlistApi.removeProductFromWishlist(productId, tokenManager.getAccessToken()!!)
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
            } finally {
                isLoading.value = false
            }
        }
    }

    fun addSharedUserToWishlist(sharedUserId: Long, onSuccess: () -> Unit) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                wishlistApi.addUserToWishlist(sharedUserId, tokenManager.getAccessToken()!!)
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
            } finally {
                isLoading.value = false
            }
        }
    }

    fun removeSharedUserFromWishlist(sharedUserId: Long, onSuccess: () -> Unit) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                wishlistApi.removeUserFromWishlist(sharedUserId, tokenManager.getAccessToken()!!)
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
            } finally {
                isLoading.value = false
            }
        }
    }

    fun addAccountToWishlist(email: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                wishlistApi.addUserThroughEmailToWishlist(email, tokenManager.getAccessToken()!!)
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
        }
    }

    fun onEmailChanged(email: String) {
        _addUserState.value = _addUserState.value.copy(email = email)
        emailError = if(!AccountDto.validateEmail(email)) "Invalid email" else null
    }
}