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
    "UnusedImport",
)

package it.unical.informatica.ea.sefora_frontend.apis

import it.unical.informatica.ea.sefora_frontend.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import it.unical.informatica.ea.sefora_frontend.infrastructure.ApiClient
import it.unical.informatica.ea.sefora_frontend.infrastructure.ApiResponse
import it.unical.informatica.ea.sefora_frontend.infrastructure.ClientError
import it.unical.informatica.ea.sefora_frontend.infrastructure.ClientException
import it.unical.informatica.ea.sefora_frontend.infrastructure.MultiValueMap
import it.unical.informatica.ea.sefora_frontend.infrastructure.RequestConfig
import it.unical.informatica.ea.sefora_frontend.infrastructure.RequestMethod
import it.unical.informatica.ea.sefora_frontend.infrastructure.ResponseType
import it.unical.informatica.ea.sefora_frontend.infrastructure.ServerError
import it.unical.informatica.ea.sefora_frontend.infrastructure.ServerException
import it.unical.informatica.ea.sefora_frontend.infrastructure.Success
import it.unical.informatica.ea.sefora_frontend.models.CartDto
import java.io.IOException

class CartControllerApi(
    basePath: kotlin.String = defaultBasePath,
    client: OkHttpClient = ApiClient.defaultClient,
) : ApiClient(basePath, client) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(ApiClient.baseUrlKey, BuildConfig.SERVER_ADDRESS)
        }
    }

    /**
     *
     *
     * @param cartId
     * @param productId
     * @return kotlin.String
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(
        IllegalStateException::class,
        IOException::class,
        UnsupportedOperationException::class,
        ClientException::class,
        ServerException::class,
    )
    suspend fun addProductToCart(
        cartId: kotlin.Long,
        productId: kotlin.Long,
    ): kotlin.String = withContext(Dispatchers.IO){
        val localVarResponse = addProductToCartWithHttpInfo(cartId = cartId, productId = productId)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as kotlin.String
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException(
                    "Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}",
                    localVarError.statusCode,
                    localVarResponse,
                )
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException(
                    "Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()} ${localVarError.body}",
                    localVarError.statusCode,
                    localVarResponse,
                )
            }
        }
    }

    /**
     *
     *
     * @param cartId
     * @param productId
     * @return ApiResponse<kotlin.String?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun addProductToCartWithHttpInfo(
        cartId: kotlin.Long,
        productId: kotlin.Long,
    ): ApiResponse<kotlin.String?> {
        val localVariableConfig = addProductToCartRequestConfig(cartId = cartId, productId = productId)

        return request<Unit, kotlin.String>(
            localVariableConfig,
        )
    }

    /**
     * To obtain the request config of the operation addProductToCart
     *
     * @param cartId
     * @param productId
     * @return RequestConfig
     */
    fun addProductToCartRequestConfig(
        cartId: kotlin.Long,
        productId: kotlin.Long,
    ): RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap =
            mutableMapOf<kotlin.String, kotlin.collections.List<kotlin.String>>()
                .apply {
                    put("cartId", listOf(cartId.toString()))
                    put("productId", listOf(productId.toString()))
                }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.PATCH,
            path = "/api/cart/addProduct",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody,
        )
    }

    /**
     *
     *
     * @param cartId
     * @return kotlin.String
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(
        IllegalStateException::class,
        IOException::class,
        UnsupportedOperationException::class,
        ClientException::class,
        ServerException::class,
    )
    suspend fun checkoutCart(cartId: kotlin.Long): kotlin.String = withContext(Dispatchers.IO){
        val localVarResponse = checkoutCartWithHttpInfo(cartId = cartId)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as kotlin.String
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException(
                    "Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}",
                    localVarError.statusCode,
                    localVarResponse,
                )
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException(
                    "Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()} ${localVarError.body}",
                    localVarError.statusCode,
                    localVarResponse,
                )
            }
        }
    }

    /**
     *
     *
     * @param cartId
     * @return ApiResponse<kotlin.String?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun checkoutCartWithHttpInfo(cartId: kotlin.Long): ApiResponse<kotlin.String?> {
        val localVariableConfig = checkoutCartRequestConfig(cartId = cartId)

        return request<Unit, kotlin.String>(
            localVariableConfig,
        )
    }

    /**
     * To obtain the request config of the operation checkoutCart
     *
     * @param cartId
     * @return RequestConfig
     */
    fun checkoutCartRequestConfig(cartId: kotlin.Long): RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap =
            mutableMapOf<kotlin.String, kotlin.collections.List<kotlin.String>>()
                .apply {
                    put("cartId", listOf(cartId.toString()))
                }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.PATCH,
            path = "/api/cart/checkout",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody,
        )
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
    @Suppress("UNCHECKED_CAST")
    @Throws(
        IllegalStateException::class,
        IOException::class,
        UnsupportedOperationException::class,
        ClientException::class,
        ServerException::class,
    )
    suspend fun createCart(cartDto: CartDto): CartDto = withContext(Dispatchers.IO){
        val localVarResponse = createCartWithHttpInfo(cartDto = cartDto)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as CartDto
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException(
                    "Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}",
                    localVarError.statusCode,
                    localVarResponse,
                )
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException(
                    "Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()} ${localVarError.body}",
                    localVarError.statusCode,
                    localVarResponse,
                )
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
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun createCartWithHttpInfo(cartDto: CartDto): ApiResponse<CartDto?> {
        val localVariableConfig = createCartRequestConfig(cartDto = cartDto)

        return request<CartDto, CartDto>(
            localVariableConfig,
        )
    }

    /**
     * To obtain the request config of the operation createCart
     *
     * @param cartDto
     * @return RequestConfig
     */
    fun createCartRequestConfig(cartDto: CartDto): RequestConfig<CartDto> {
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
            body = localVariableBody,
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
    @Suppress("UNCHECKED_CAST")
    @Throws(
        IllegalStateException::class,
        IOException::class,
        UnsupportedOperationException::class,
        ClientException::class,
        ServerException::class,
    )
    suspend fun getCartById(id: kotlin.Long): CartDto = withContext(Dispatchers.IO){
        val localVarResponse = getCartByIdWithHttpInfo(id = id)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as CartDto
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException(
                    "Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}",
                    localVarError.statusCode,
                    localVarResponse,
                )
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException(
                    "Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()} ${localVarError.body}",
                    localVarError.statusCode,
                    localVarResponse,
                )
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
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun getCartByIdWithHttpInfo(id: kotlin.Long): ApiResponse<CartDto?> {
        val localVariableConfig = getCartByIdRequestConfig(id = id)

        return request<Unit, CartDto>(
            localVariableConfig,
        )
    }

    /**
     * To obtain the request config of the operation getCartById
     *
     * @param id
     * @return RequestConfig
     */
    fun getCartByIdRequestConfig(id: kotlin.Long): RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/api/cart/{id}".replace("{" + "id" + "}", encodeURIComponent(id.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody,
        )
    }

    /**
     *
     *
     * @return CartDto
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(
        IllegalStateException::class,
        IOException::class,
        UnsupportedOperationException::class,
        ClientException::class,
        ServerException::class,
    )
    suspend fun getCurrentUserCart(): CartDto = withContext(Dispatchers.IO){
        val localVarResponse = getCurrentUserCartWithHttpInfo()

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as CartDto
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException(
                    "Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}",
                    localVarError.statusCode,
                    localVarResponse,
                )
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException(
                    "Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()} ${localVarError.body}",
                    localVarError.statusCode,
                    localVarResponse,
                )
            }
        }
    }

    /**
     *
     *
     * @return ApiResponse<CartDto?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun getCurrentUserCartWithHttpInfo(): ApiResponse<CartDto?> {
        val localVariableConfig = getCurrentUserCartRequestConfig()

        return request<Unit, CartDto>(
            localVariableConfig,
        )
    }

    /**
     * To obtain the request config of the operation getCurrentUserCart
     *
     * @return RequestConfig
     */
    fun getCurrentUserCartRequestConfig(): RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/api/cart/current",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody,
        )
    }

    /**
     *
     *
     * @param cartId
     * @param productId
     * @return kotlin.String
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(
        IllegalStateException::class,
        IOException::class,
        UnsupportedOperationException::class,
        ClientException::class,
        ServerException::class,
    )
    suspend fun removeProductFromCart(
        cartId: kotlin.Long,
        productId: kotlin.Long,
    ): kotlin.String = withContext(Dispatchers.IO){
        val localVarResponse = removeProductFromCartWithHttpInfo(cartId = cartId, productId = productId)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as kotlin.String
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException(
                    "Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}",
                    localVarError.statusCode,
                    localVarResponse,
                )
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException(
                    "Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()} ${localVarError.body}",
                    localVarError.statusCode,
                    localVarResponse,
                )
            }
        }
    }

    /**
     *
     *
     * @param cartId
     * @param productId
     * @return ApiResponse<kotlin.String?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun removeProductFromCartWithHttpInfo(
        cartId: kotlin.Long,
        productId: kotlin.Long,
    ): ApiResponse<kotlin.String?> {
        val localVariableConfig = removeProductFromCartRequestConfig(cartId = cartId, productId = productId)

        return request<Unit, kotlin.String>(
            localVariableConfig,
        )
    }

    /**
     * To obtain the request config of the operation removeProductFromCart
     *
     * @param cartId
     * @param productId
     * @return RequestConfig
     */
    fun removeProductFromCartRequestConfig(
        cartId: kotlin.Long,
        productId: kotlin.Long,
    ): RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap =
            mutableMapOf<kotlin.String, kotlin.collections.List<kotlin.String>>()
                .apply {
                    put("cartId", listOf(cartId.toString()))
                    put("productId", listOf(productId.toString()))
                }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.PATCH,
            path = "/api/cart/removeProduct",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody,
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
    @Suppress("UNCHECKED_CAST")
    @Throws(
        IllegalStateException::class,
        IOException::class,
        UnsupportedOperationException::class,
        ClientException::class,
        ServerException::class,
    )
    suspend fun updateCart(cartDto: CartDto): kotlin.String = withContext(Dispatchers.IO){
        val localVarResponse = updateCartWithHttpInfo(cartDto = cartDto)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as kotlin.String
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException(
                    "Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}",
                    localVarError.statusCode,
                    localVarResponse,
                )
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException(
                    "Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()} ${localVarError.body}",
                    localVarError.statusCode,
                    localVarResponse,
                )
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
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun updateCartWithHttpInfo(cartDto: CartDto): ApiResponse<kotlin.String?> {
        val localVariableConfig = updateCartRequestConfig(cartDto = cartDto)

        return request<CartDto, kotlin.String>(
            localVariableConfig,
        )
    }

    /**
     * To obtain the request config of the operation updateCart
     *
     * @param cartDto
     * @return RequestConfig
     */
    fun updateCartRequestConfig(cartDto: CartDto): RequestConfig<CartDto> {
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
            body = localVariableBody,
        )
    }

    private fun encodeURIComponent(uriComponent: kotlin.String): kotlin.String =
        HttpUrl
            .Builder()
            .scheme("http")
            .host("localhost")
            .addPathSegment(uriComponent)
            .build()
            .encodedPathSegments[0]
}
