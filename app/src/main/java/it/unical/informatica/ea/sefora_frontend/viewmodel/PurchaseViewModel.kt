package it.unical.informatica.ea.sefora_frontend.viewmodel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.unical.informatica.ea.sefora_frontend.apis.OrderControllerApi
import it.unical.informatica.ea.sefora_frontend.apis.OrderProductControllerApi
import it.unical.informatica.ea.sefora_frontend.auth.TokenManager
import it.unical.informatica.ea.sefora_frontend.models.OrderDto
import it.unical.informatica.ea.sefora_frontend.models.OrderProductDto
import it.unical.informatica.ea.sefora_frontend.models.UserDto
import kotlinx.coroutines.launch

class OrderViewModel(val context : Context) :ViewModel() {

    private val orderApi: OrderControllerApi = OrderControllerApi()
    private val orderProductControllerApi = OrderProductControllerApi()
    private val tokenManager = TokenManager(context)
    val orderList: MutableState<Array<Pair<OrderDto, List<OrderProductDto>>>> = mutableStateOf(emptyArray())
    var currentUser: UserDto? = null

    init {
        viewModelScope.launch {
            currentUser = tokenManager.getUser()
            getOrders(currentUser!!.id!!)
        }
    }

    fun getOrders(id: Long) {
        viewModelScope.launch {
            val orders = orderApi.findOrdersbyUserId(id)
            orderList.value = orders.map { order ->
                order to orderProductControllerApi.getOrderProductsByOrderId(order.id!!)
            }.toTypedArray()
        }
    }
}