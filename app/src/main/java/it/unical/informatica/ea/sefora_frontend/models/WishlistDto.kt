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
import com.squareup.moshi.JsonClass

/**
 *
 *
 * @param userWishlistId
 * @param products
 * @param type
 * @param id
 * @param name
 * @param sharedWithUsers
 */

data class WishlistDto(
    @Json(name = "userWishlistId")
    val userWishlistId: kotlin.Long,
    @Json(name = "products")
    val products: kotlin.collections.List<ProductShortDto>,
    @Json(name = "type")
    val type: WishlistDto.Type,
    @Json(name = "id")
    val id: kotlin.Long? = null,
    @Json(name = "name")
    val name: kotlin.String? = null,
    @Json(name = "sharedWithUsers")
    val sharedWithUsers: kotlin.collections.List<AccountShortDto>? = null,
) {
    /**
     *
     *
     * Values: PERSONAL,PUBLIC,SHARED
     */
    @JsonClass(generateAdapter = false)
    enum class Type(
        val value: kotlin.String,
    ) {
        @Json(name = "PERSONAL")
        PERSONAL("PERSONAL"),

        @Json(name = "PUBLIC")
        PUBLIC("PUBLIC"),

        @Json(name = "SHARED")
        SHARED("SHARED"),
    }
}
