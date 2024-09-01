package it.unical.informatica.ea.sefora_frontend.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.unical.informatica.ea.sefora_frontend.apis.PurchaseControllerApi
import it.unical.informatica.ea.sefora_frontend.auth.TokenManager
import it.unical.informatica.ea.sefora_frontend.models.PurchaseDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PurchaseState(
    val purchases: List<PurchaseDto> = emptyList()
)

@HiltViewModel
class PurchaseViewModel @Inject constructor(
    private val tokenManager: TokenManager
) :ViewModel() {
    private val purchaseApi: PurchaseControllerApi = PurchaseControllerApi()
    val isLoading = mutableStateOf(false)

    private val _purchases = MutableStateFlow(PurchaseState())
    val purchases: StateFlow<PurchaseState> = _purchases.asStateFlow()

    init {
        getPurchases()
    }

    fun getPurchases() {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val purchases = purchaseApi.findOrdersByCurrentUser(tokenManager.getAccessToken()!!)
                _purchases.value = _purchases.value.copy(
                    purchases = purchases
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            isLoading.value = false
        }
    }
    fun convertProductToPurchase(productId: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                purchaseApi.convertProductToPurchase(productId, tokenManager.getAccessToken()!!)
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}