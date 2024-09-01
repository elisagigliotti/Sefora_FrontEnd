package it.unical.informatica.ea.sefora_frontend.infrastructure

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.math.BigInteger

class BigIntegerAdapter {
    @ToJson
    fun toJson(value: BigInteger): String = value.toString()

    @FromJson
    fun fromJson(value: String): BigInteger = BigInteger(value)
}
