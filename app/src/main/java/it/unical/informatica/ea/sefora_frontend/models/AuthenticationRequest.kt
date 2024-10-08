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

package it.unical.informatica.ea.sefora_frontend.models

import com.squareup.moshi.Json

/**
 *
 *
 * @param password
 * @param email
 */

data class AuthenticationRequest(
    @Json(name = "password")
    val password: kotlin.String? = null,
    @Json(name = "email")
    val email: kotlin.String? = null,
    @Json(name = "idToken")
    val idToken: kotlin.String? = null,
)
