package it.unical.informatica.ea.sefora_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.unical.informatica.ea.sefora_frontend.apis.ProductControllerApi
import it.unical.informatica.ea.sefora_frontend.auth.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import it.unical.informatica.ea.sefora_frontend.models.ProductDto
import javax.inject.Inject

data class AllProductsState(
    val articoli: List<ProductDto> = emptyList()
)

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    val tokenManager: TokenManager
): ViewModel() {
    private val productApi = ProductControllerApi()
    val isLoading = mutableStateOf(false)

    private val _articoli = MutableStateFlow(AllProductsState())
    val allProductsState: StateFlow<AllProductsState> = _articoli.asStateFlow()

    init {
        getArticoli()
    }

    fun getArticoli() {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val articoli = productApi.getAllProducts()
                _articoli.value = _articoli.value.copy(
                    articoli = articoli
                )
            } catch (e: Exception) {
                Log.e("HomeScreenViewModel", "getArticoli: ${e.message}")
            }
            isLoading.value = false
        }
    }
}