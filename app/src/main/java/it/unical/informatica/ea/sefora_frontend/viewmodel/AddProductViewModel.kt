package it.unical.informatica.ea.sefora_frontend.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import it.unical.informatica.ea.sefora_frontend.models.ProductDto.Category
import javax.inject.Inject

data class AddProductState(
    val name: String = "",
    val category: Category? = null,
    val description: String = "",
    val price: Float = 0f,
    val imageProduct: Bitmap? = null,
)

@HiltViewModel
class AddProductViewModel @Inject constructor(
    val tokenManager: TokenManager
) : ViewModel() {
    private val productApi = ProductControllerApi()
    private val _productUpd = MutableStateFlow(AddProductState())
    val productState: StateFlow<AddProductState> = _productUpd.asStateFlow()

    var nameError by mutableStateOf<String?>(null)
    var categoryError by mutableStateOf<String?>(null)
    var descriptionError by mutableStateOf<String?>(null)
    var priceError by mutableStateOf<String?>(null)
    var imageProductError by mutableStateOf<String?>(null)

    fun onNameChanged(name: String) {
        _productUpd.value = _productUpd.value.copy(name = name)
        nameError = if(name.isEmpty()) "Name cannot be empty" else null
    }

    fun onCategoryChanged(category: Category) {
        _productUpd.value = _productUpd.value.copy(category = category)
    }

    fun onDescriptionChanged(description: String) {
        _productUpd.value = _productUpd.value.copy(description = description)
        descriptionError = if(description.length < 50 || description.length > 1000) "Description length must be between 50 and 1000 characters" else null
    }

    fun onPriceChanged(price: Float) {
        _productUpd.value = _productUpd.value.copy(price = price)
        priceError = if(price < 0f) "Price cannot be less than 0" else null
    }

    fun onImageProductChanged(imageProduct: Bitmap?) {
        _productUpd.value = _productUpd.value.copy(imageProduct = imageProduct)
    }

    fun createProduct(
        name: String,
        category: Category,
        description: String?,
        price: Float,
        imageProduct: Bitmap?,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val productDto = ProductDto(
                    name = name,
                    category = category,
                    userProductId = tokenManager.getAccount()!!.id!!,
                    description = description,
                    price = price,
                    imageProduct = imageProduct
                )
                productApi.createProduct(productDto, tokenManager.getAccessToken()!!)
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                onFailure()
            }
        }
    }
}