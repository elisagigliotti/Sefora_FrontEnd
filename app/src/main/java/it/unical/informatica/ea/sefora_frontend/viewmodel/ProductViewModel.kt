package it.unical.informatica.ea.sefora_frontend.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import it.unical.informatica.ea.sefora_frontend.apis.ProductControllerApi
import it.unical.informatica.ea.sefora_frontend.auth.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import it.unical.informatica.ea.sefora_frontend.models.ProductDto
import javax.inject.Inject

data class ProductState(
    val name: String="",
    val price: Double=0.0,
    val description: String="",
    val image: List<Bitmap> = listOf(),
    val category: ProductDto.Category?= null,
    val quantity: Int=0,
)

@HiltViewModel
class ProductViewModel @Inject constructor(
    val tokenManager: TokenManager,
) : ViewModel() {

    private val productApi: ProductControllerApi = ProductControllerApi()
    private val _productState = MutableStateFlow(ProductState())
    val productState: StateFlow<ProductState> = _productState.asStateFlow()
    private val _product = MutableStateFlow<ProductDto?>(null)
    val product: StateFlow<ProductDto?> = _product.asStateFlow()
}