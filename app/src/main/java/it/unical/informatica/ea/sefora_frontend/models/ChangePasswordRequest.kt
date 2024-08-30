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

package org.openapitools.client.models

import com.squareup.moshi.Json

/**
 *
 *
 * @param currentPassword
 * @param newPassword
 * @param confirmationPassword
 */

data class ChangePasswordRequest(
    @Json(name = "currentPassword")
    val currentPassword: kotlin.String? = null,
    @Json(name = "newPassword")
    val newPassword: kotlin.String? = null,
    @Json(name = "confirmationPassword")
    val confirmationPassword: kotlin.String? = null,
)
