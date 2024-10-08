package it.unical.informatica.ea.sefora_frontend.infrastructure

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object Serializer {
    @JvmStatic
    val moshiBuilder: Moshi.Builder =
        Moshi
            .Builder()
            .add(OffsetDateTimeAdapter())
            .add(LocalDateTimeAdapter())
            .add(LocalDateAdapter())
            .add(UUIDAdapter())
            .add(ByteArrayAdapter())
            .add(URIAdapter())
            .add(KotlinJsonAdapterFactory())
            .add(BigDecimalAdapter())
            .add(BigIntegerAdapter())
            .add(BitmapConverter())

    @JvmStatic
    val moshi: Moshi by lazy {
        moshiBuilder.build()
    }
}
