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
import it.unical.informatica.ea.sefora_frontend.models.PurchaseDto
import kotlinx.coroutines.runBlocking
import java.io.IOException

class PurchaseControllerApi(
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
     * @param id
     * @return PurchaseDto
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun convertProductToPurchase(id: kotlin.Long, token: String) : PurchaseDto = runBlocking(Dispatchers.IO) {
        val localVarResponse = convertProductToPurchaseWithHttpInfo(id = id, token = token)

        return@runBlocking when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as PurchaseDto
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
     * @return ApiResponse<PurchaseDto?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun convertProductToPurchaseWithHttpInfo(id: kotlin.Long, token: String) : ApiResponse<PurchaseDto?> {
        val localVariableConfig = convertProductToPurchaseRequestConfig(id = id, token = token)

        return request<Unit, PurchaseDto>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation convertProductToPurchase
     *
     * @param id
     * @return RequestConfig
     */
    fun convertProductToPurchaseRequestConfig(id: kotlin.Long, token: String) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        localVariableHeaders["Authorization"] = "Bearer $token"

        return RequestConfig(
            method = RequestMethod.PATCH,
            path = "/api/order/convert/{id}".replace("{"+"id"+"}", encodeURIComponent(id.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }

    /**
     *
     *
     * @param purchaseDto
     * @return PurchaseDto
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
    suspend fun createOrder(purchaseDto: PurchaseDto): PurchaseDto = withContext(Dispatchers.IO){
        val localVarResponse = createOrderWithHttpInfo(purchaseDto = purchaseDto)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as PurchaseDto
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
     * @param purchaseDto
     * @return ApiResponse<PurchaseDto?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun createOrderWithHttpInfo(purchaseDto: PurchaseDto): ApiResponse<PurchaseDto?> {
        val localVariableConfig = createOrderRequestConfig(purchaseDto = purchaseDto)

        return request<PurchaseDto, PurchaseDto>(
            localVariableConfig,
        )
    }

    /**
     * To obtain the request config of the operation createOrder
     *
     * @param purchaseDto
     * @return RequestConfig
     */
    fun createOrderRequestConfig(purchaseDto: PurchaseDto): RequestConfig<PurchaseDto> {
        val localVariableBody = purchaseDto
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/api/order",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody,
        )
    }

    /**
     *
     *
     * @return kotlin.collections.List<PurchaseDto>
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
    suspend fun findOrdersByCurrentUser(token: String): kotlin.collections.List<PurchaseDto> = withContext(Dispatchers.IO){
        val localVarResponse = findOrdersByCurrentUserWithHttpInfo(token = token)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as kotlin.collections.List<PurchaseDto>
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
     * @return ApiResponse<kotlin.collections.List<PurchaseDto>?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun findOrdersByCurrentUserWithHttpInfo(token: String): ApiResponse<kotlin.collections.List<PurchaseDto>?> {
        val localVariableConfig = findOrdersByCurrentUserRequestConfig(token = token)

        return request<Unit, kotlin.collections.List<PurchaseDto>>(
            localVariableConfig,
        )
    }

    /**
     * To obtain the request config of the operation findOrdersByCurrentUser
     *
     * @return RequestConfig
     */
    fun findOrdersByCurrentUserRequestConfig(token: String): RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        localVariableHeaders["Authorization"] = "Bearer $token"

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/api/order/current",
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
     * @return kotlin.collections.List<PurchaseDto>
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
    suspend fun findOrdersByUserId(id: kotlin.Long): kotlin.collections.List<PurchaseDto> = withContext(Dispatchers.IO){
        val localVarResponse = findOrdersByUserIdWithHttpInfo(id = id)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as kotlin.collections.List<PurchaseDto>
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
     * @return ApiResponse<kotlin.collections.List<PurchaseDto>?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun findOrdersByUserIdWithHttpInfo(id: kotlin.Long): ApiResponse<kotlin.collections.List<PurchaseDto>?> {
        val localVariableConfig = findOrdersByUserIdRequestConfig(id = id)

        return request<Unit, kotlin.collections.List<PurchaseDto>>(
            localVariableConfig,
        )
    }

    /**
     * To obtain the request config of the operation findOrdersByUserId
     *
     * @param id
     * @return RequestConfig
     */
    fun findOrdersByUserIdRequestConfig(id: kotlin.Long): RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/api/order/user/{id}".replace("{" + "id" + "}", encodeURIComponent(id.toString())),
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
