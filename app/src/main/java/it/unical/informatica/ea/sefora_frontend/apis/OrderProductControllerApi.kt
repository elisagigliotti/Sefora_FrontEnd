/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport", "unused"
)

package it.unical.informatica.ea.sefora_frontend.apis

import java.io.IOException
import okhttp3.OkHttpClient
import okhttp3.HttpUrl

import it.unical.informatica.ea.sefora_frontend.models.OrderProductDto
import it.unical.informatica.ea.sefora_frontend.BuildConfig
import it.unical.informatica.ea.sefora_frontend.infrastructure.ApiClient
import it.unical.informatica.ea.sefora_frontend.infrastructure.ApiResponse
import it.unical.informatica.ea.sefora_frontend.infrastructure.ClientException
import it.unical.informatica.ea.sefora_frontend.infrastructure.ClientError
import it.unical.informatica.ea.sefora_frontend.infrastructure.ServerException
import it.unical.informatica.ea.sefora_frontend.infrastructure.ServerError
import it.unical.informatica.ea.sefora_frontend.infrastructure.MultiValueMap
import it.unical.informatica.ea.sefora_frontend.infrastructure.RequestConfig
import it.unical.informatica.ea.sefora_frontend.infrastructure.RequestMethod
import it.unical.informatica.ea.sefora_frontend.infrastructure.ResponseType
import it.unical.informatica.ea.sefora_frontend.infrastructure.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrderProductControllerApi(basePath: kotlin.String = defaultBasePath, client: OkHttpClient = ApiClient.defaultClient) : ApiClient(basePath, client) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(ApiClient.baseUrlKey, BuildConfig.SERVER_ADDRESS)
        }
    }

    /**
     * 
     * 
     * @param orderProductDto 
     * @return OrderProductDto
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun createOrderProduct(orderProductDto: OrderProductDto) : OrderProductDto = withContext(Dispatchers.IO){
        val localVarResponse = createOrderProductWithHttpInfo(orderProductDto = orderProductDto)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as OrderProductDto
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }

            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()} ${localVarError.body}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
     * 
     * 
     * @param orderProductDto 
     * @return ApiResponse<OrderProductDto?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Throws(IllegalStateException::class, IOException::class)
    fun createOrderProductWithHttpInfo(orderProductDto: OrderProductDto) : ApiResponse<OrderProductDto?> {
        val localVariableConfig = createOrderProductRequestConfig(orderProductDto = orderProductDto)

        return request<OrderProductDto, OrderProductDto>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation createOrderProduct
     *
     * @param orderProductDto 
     * @return RequestConfig
     */
    fun createOrderProductRequestConfig(orderProductDto: OrderProductDto) : RequestConfig<OrderProductDto> {
        val localVariableBody = orderProductDto
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        
        return RequestConfig(
            method = RequestMethod.POST,
            path = "/api/orderProduct",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }

    /**
     * 
     * 
     * @param id 
     * @return kotlin.collections.List<OrderProductDto>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun getOrderProductsByOrderId(id: kotlin.Long) : kotlin.collections.List<OrderProductDto> = withContext(Dispatchers.IO) {
        val localVarResponse = getOrderProductsByOrderIdWithHttpInfo(id = id)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as kotlin.collections.List<OrderProductDto>
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }

            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()} ${localVarError.body}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
     * 
     * 
     * @param id 
     * @return ApiResponse<kotlin.collections.List<OrderProductDto>?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Throws(IllegalStateException::class, IOException::class)
    fun getOrderProductsByOrderIdWithHttpInfo(id: kotlin.Long) : ApiResponse<kotlin.collections.List<OrderProductDto>?> {
        val localVariableConfig = getOrderProductsByOrderIdRequestConfig(id = id)

        return request<Unit, kotlin.collections.List<OrderProductDto>>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getOrderProductsByOrderId
     *
     * @param id 
     * @return RequestConfig
     */
    fun getOrderProductsByOrderIdRequestConfig(id: kotlin.Long) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        
        return RequestConfig(
            method = RequestMethod.GET,
            path = "/api/orderProduct/order/{id}".replace("{"+"id"+"}", encodeURIComponent(id.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }


    private fun encodeURIComponent(uriComponent: kotlin.String): kotlin.String =
        HttpUrl.Builder().scheme("http").host("localhost").addPathSegment(uriComponent).build().encodedPathSegments[0]
}
