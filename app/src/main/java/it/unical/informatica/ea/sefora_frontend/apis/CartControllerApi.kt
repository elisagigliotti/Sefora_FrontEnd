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

import it.unical.informatica.ea.sefora_frontend.models.CartDto
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

class CartControllerApi(basePath: kotlin.String = defaultBasePath, client: OkHttpClient = ApiClient.defaultClient) : ApiClient(basePath, client) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(ApiClient.baseUrlKey, BuildConfig.SERVER_ADDRESS)
        }
    }

    /**
     * 
     * 
     * @param cartDto 
     * @return CartDto
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun createCart(cartDto: CartDto) : CartDto = withContext(Dispatchers.IO){
        val localVarResponse = createCartWithHttpInfo(cartDto = cartDto)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as CartDto
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
     * @param cartDto 
     * @return ApiResponse<CartDto?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Throws(IllegalStateException::class, IOException::class)
    fun createCartWithHttpInfo(cartDto: CartDto) : ApiResponse<CartDto?> {
        val localVariableConfig = createCartRequestConfig(cartDto = cartDto)

        return request<CartDto, CartDto>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation createCart
     *
     * @param cartDto 
     * @return RequestConfig
     */
    fun createCartRequestConfig(cartDto: CartDto) : RequestConfig<CartDto> {
        val localVariableBody = cartDto
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        
        return RequestConfig(
            method = RequestMethod.POST,
            path = "/api/cart",
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
     * @return CartDto
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun getCartById(id: kotlin.Long) : CartDto = withContext(Dispatchers.IO){
        val localVarResponse = getCartByIdWithHttpInfo(id = id)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as CartDto
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
     * @return ApiResponse<CartDto?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Throws(IllegalStateException::class, IOException::class)
    fun getCartByIdWithHttpInfo(id: kotlin.Long) : ApiResponse<CartDto?> {
        val localVariableConfig = getCartByIdRequestConfig(id = id)

        return request<Unit, CartDto>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getCartById
     *
     * @param id 
     * @return RequestConfig
     */
    fun getCartByIdRequestConfig(id: kotlin.Long) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        
        return RequestConfig(
            method = RequestMethod.GET,
            path = "/api/cart/{id}".replace("{"+"id"+"}", encodeURIComponent(id.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }

    /**
     * 
     * 
     * @param cartDto 
     * @return kotlin.String
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun updateCart(cartDto: CartDto) : kotlin.String = withContext(Dispatchers.IO){
        val localVarResponse = updateCartWithHttpInfo(cartDto = cartDto)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as kotlin.String
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
     * @param cartDto 
     * @return ApiResponse<kotlin.String?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Throws(IllegalStateException::class, IOException::class)
    fun updateCartWithHttpInfo(cartDto: CartDto) : ApiResponse<kotlin.String?> {
        val localVariableConfig = updateCartRequestConfig(cartDto = cartDto)

        return request<CartDto, kotlin.String>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation updateCart
     *
     * @param cartDto 
     * @return RequestConfig
     */
    fun updateCartRequestConfig(cartDto: CartDto) : RequestConfig<CartDto> {
        val localVariableBody = cartDto
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        
        return RequestConfig(
            method = RequestMethod.PUT,
            path = "/api/cart",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }


    private fun encodeURIComponent(uriComponent: kotlin.String): kotlin.String =
        HttpUrl.Builder().scheme("http").host("localhost").addPathSegment(uriComponent).build().encodedPathSegments[0]
}
