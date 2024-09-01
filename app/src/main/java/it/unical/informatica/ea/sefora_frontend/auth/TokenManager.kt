package it.unical.informatica.ea.sefora_frontend.auth

import android.content.Context
import android.security.keystore.KeyProperties
import android.security.keystore.KeyProtection
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.auth0.android.jwt.JWT
import it.unical.informatica.ea.sefora_frontend.BuildConfig
import it.unical.informatica.ea.sefora_frontend.apis.AccountControllerApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import it.unical.informatica.ea.sefora_frontend.infrastructure.Serializer
import it.unical.informatica.ea.sefora_frontend.models.AccountDto
import java.security.KeyStore
import java.util.Base64
import java.util.Date
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "account_prefs")

class TokenManager @Inject constructor(
    private val context: Context
) {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        private val USER = stringPreferencesKey("account")
        private val EXPIRES_IN = longPreferencesKey("expires_in")
        private const val KEYSTORE_ALIAS = "MyAppKeyAlias"
        private const val ENCRYPTION_ALGORITHM = "AES/GCM/NoPadding"
        private const val ENCRYPTION_KEY_SIZE = 256
        private const val ENCRYPTION_IV_SIZE = 12

        private val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }

        init {
            if (!keyStore.containsAlias(KEYSTORE_ALIAS)) {
                val keyGenerator = KeyGenerator.getInstance("AES")
                keyGenerator.init(ENCRYPTION_KEY_SIZE)
                val secretKey = keyGenerator.generateKey()

                // Define KeyProtection for the key
                val keyProtection = KeyProtection.Builder(KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build()

                // Store the key in the KeyStore with protection parameters
                keyStore.setEntry(KEYSTORE_ALIAS, KeyStore.SecretKeyEntry(secretKey), keyProtection)
            }
        }
    }

    private val moshi = Serializer.moshi
    private val accountAdapter = moshi.adapter(AccountDto::class.java)

    // Save tokens with encryption
    suspend fun saveTokens(
        accessToken: String,
        refreshToken: String,
        account: AccountDto,
        expires_in: Long
    ) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = encryptToken(accessToken)
            preferences[REFRESH_TOKEN_KEY] = encryptToken(refreshToken)
            preferences[USER] = accountAdapter.toJson(account)
            preferences[EXPIRES_IN] = expires_in
        }
        logTokenActivity("Tokens saved successfully.")
    }

    // Retrieve account info
    suspend fun getAccount(): AccountDto? {
        val accountString = context.dataStore.data.map { preferences ->
            preferences[USER]
        }.firstOrNull()

        return accountString?.let {
            accountAdapter.fromJson(it)
        }
    }

    // Clear tokens and logout
    suspend fun logout() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
        logTokenActivity("User logged out.")
    }

    // Access token flow
    val accessTokenFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            decryptToken(preferences[ACCESS_TOKEN_KEY])
        }

    // Refresh token flow
    val refreshTokenFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            decryptToken(preferences[REFRESH_TOKEN_KEY])
        }

    // Check login status flow
    val isLoggedInFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            val accessToken = decryptToken(preferences[ACCESS_TOKEN_KEY])
            !accessToken.isNullOrEmpty()
        }

    // Check if the access token is expired
    fun isAccessTokenExpired(): Boolean {
        val accessToken = getAccessToken()
        if (accessToken.isNullOrEmpty()) {
            return true
        }

        return try {
            val jwt = JWT(accessToken)
            val expiresAt = jwt.expiresAt
            expiresAt != null && expiresAt.before(Date())
        } catch (e: Exception) {
            e.printStackTrace()
            true
        }
    }

    // Get access token (decrypted)
    fun getAccessToken(): String? {
        var token: String?
        runBlocking {
            val preferences = context.dataStore.data.first()
            token = decryptToken(preferences[ACCESS_TOKEN_KEY])
        }
        return token
    }

    // Get refresh token (decrypted)
    fun getRefreshToken(): String? {
        var token: String?
        runBlocking {
            val preferences = context.dataStore.data.first()
            token = decryptToken(preferences[REFRESH_TOKEN_KEY])
        }
        return token
    }

    // Refresh access token if expired
    suspend fun refreshAccessTokenIfNeeded(): Boolean {
        if (isAccessTokenExpired()) {
            val refreshToken = getRefreshToken()
            if (!refreshToken.isNullOrEmpty()) {
                try {
                    // Call API to refresh access token
                    val apiService: AccountControllerApi = AccountControllerApi( // Inject AccountControllerApi
                        basePath = BuildConfig.SERVER_ADDRESS,
                        client = OkHttpClient.Builder().build(),
                    )
                    val newTokens = apiService.refreshToken()
                    saveTokens(
                        newTokens.accessToken,
                        newTokens.refreshToken,
                        newTokens.account,
                        newTokens.expiresIn
                    )
                    logTokenActivity("Access token refreshed successfully.")
                    return true
                } catch (e: Exception) {
                    e.printStackTrace()
                    logTokenActivity("Failed to refresh access token.")
                }
            }
        }
        return false
    }

    // Encrypt token
    private fun encryptToken(token: String): String {
        val cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM)
        val secretKey = keyStore.getKey(KEYSTORE_ALIAS, null) as SecretKey
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encryptedData = cipher.doFinal(token.toByteArray(Charsets.UTF_8))
        val combinedData = iv + encryptedData
        return Base64.getEncoder().encodeToString(combinedData)
    }

    // Decrypt token
    private fun decryptToken(encryptedToken: String?): String? {
        if (encryptedToken == null) return null
        val cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM)
        val secretKey = keyStore.getKey(KEYSTORE_ALIAS, null) as SecretKey
        val decodedData = Base64.getDecoder().decode(encryptedToken)
        val iv = decodedData.sliceArray(0 until ENCRYPTION_IV_SIZE)
        val encryptedData = decodedData.sliceArray(ENCRYPTION_IV_SIZE until decodedData.size)
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
        val decryptedData = cipher.doFinal(encryptedData)
        return String(decryptedData, Charsets.UTF_8)
    }

    // Validate token structure
    fun isTokenValid(token: String): Boolean {
        return try {
            val jwt = JWT(token)
            !jwt.isExpired(10) // Checks if expired with 10 seconds of leeway
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Log token activities
    private fun logTokenActivity(message: String) {
        Log.d("TokenManager", message)
    }

    // Get user roles from token
    fun getUserRoles(): List<String>? {
        val accessToken = getAccessToken()
        return if (!accessToken.isNullOrEmpty()) {
            val jwt = JWT(accessToken)
            jwt.getClaim("roles").asList(String::class.java)
        } else {
            null
        }
    }

    // Check if user has a specific role
    fun hasRole(role: String): Boolean {
        val roles = getUserRoles()
        return roles?.contains(role) ?: false
    }

    // New Method: Get token expiration date
    fun getTokenExpirationDate(): Date? {
        val accessToken = getAccessToken()
        return accessToken?.let {
            val jwt = JWT(it)
            jwt.expiresAt
        }
    }

    // New Method: Force logout if token is invalid
    suspend fun forceLogoutIfInvalid() {
        if (!isTokenValid(getAccessToken() ?: "")) {
            logout()
            logTokenActivity("Forced logout due to invalid token.")
        }
    }

    // New Method: Get time left until token expires (in milliseconds)
    fun getTimeUntilTokenExpires(): Long {
        val expirationDate = getTokenExpirationDate()
        return expirationDate?.time?.minus(System.currentTimeMillis()) ?: 0
    }

    // Clear only tokens (access token and refresh token)
    suspend fun clearTokens() {
        context.dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
            preferences.remove(REFRESH_TOKEN_KEY)
            preferences.remove(EXPIRES_IN)
            preferences.remove(USER)
        }
        logTokenActivity("Tokens cleared successfully.")
    }

}
