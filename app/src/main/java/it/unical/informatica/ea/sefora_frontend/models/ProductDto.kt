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

import android.graphics.Bitmap
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 *
 *
 * @param name
 * @param category
 * @param userProductId
 * @param id
 * @param description
 * @param price
 * @param imageProduct
 */
@JsonClass(generateAdapter = true)
data class ProductDto(
    @Json(name = "name")
    val name: kotlin.String,
    @Json(name = "category")
    val category: ProductDto.Category,
    @Json(name = "userProductId")
    val userProductId: kotlin.Long,
    @Json(name = "id")
    val id: kotlin.Long? = null,
    @Json(name = "description")
    val description: kotlin.String? = null,
    @Json(name = "price")
    val price: kotlin.Float? = null,
    @Json(name = "imageProduct")
    val imageProduct: Bitmap? = null,
) {
    /**
     *
     *
     * Values: MAKEUP,SKINCARE,HAIRCARE,FRAGRANCE,BATH_BODY
     */
    @JsonClass(generateAdapter = false)
    enum class Category(
        val value: kotlin.String,
    ) {
        @Json(name = "MAKEUP")
        MAKEUP("MAKEUP"),

        @Json(name = "SKINCARE")
        SKINCARE("SKINCARE"),

        @Json(name = "HAIRCARE")
        HAIRCARE("HAIRCARE"),

        @Json(name = "FRAGRANCE")
        FRAGRANCE("FRAGRANCE"),

        @Json(name = "BATH_BODY")
        BATH_BODY("BATH_BODY"),
    }
}
