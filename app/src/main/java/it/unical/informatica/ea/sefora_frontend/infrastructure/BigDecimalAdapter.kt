package it.unical.informatica.ea.sefora_frontend.infrastructure

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.math.BigDecimal

class BigDecimalAdapter {
    @ToJson
    fun toJson(value: BigDecimal): String = value.toPlainString()

    @FromJson
    fun fromJson(value: String): BigDecimal = BigDecimal(value)
}
