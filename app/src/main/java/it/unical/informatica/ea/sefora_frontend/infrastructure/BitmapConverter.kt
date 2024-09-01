package it.unical.informatica.ea.sefora_frontend.infrastructure

import android.graphics.Bitmap
import android.util.Log
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonEncodingException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.io.ByteArrayOutputStream

class BitmapConverter {
    private val _encodedImage: String = "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"

    @FromJson
    fun fromJson(reader: JsonReader): Bitmap? {
        reader.setLenient(true)
        try {
            val base64 = reader.nextString()
            val data = android.util.Base64.decode(base64, android.util.Base64.DEFAULT)
            return android.graphics.BitmapFactory.decodeByteArray(data, 0, data.size)
        } catch (e: JsonEncodingException) {
            Log.e("BitmapConverter", "Malformed JSON at path: ${reader.path}", e)
            val decodedString = android.util.Base64.decode(_encodedImage, android.util.Base64.DEFAULT)
            return android.graphics.BitmapFactory.decodeByteArray(
                decodedString,
                0,
                decodedString.size
            )
        } catch (e: Exception) {
            Log.w("BitmapConverter", "Failed to decode image", e)
            val decodedString = android.util.Base64.decode(_encodedImage, android.util.Base64.DEFAULT)
            return android.graphics.BitmapFactory.decodeByteArray(
                decodedString,
                0,
                decodedString.size
            )
        }
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: Bitmap?) {
        if (value != null) {
            val outputStream = ByteArrayOutputStream()
            value.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            val data = outputStream.toByteArray()
            val base64 = android.util.Base64.encodeToString(data, android.util.Base64.DEFAULT)
            writer.value(base64)
        } else {
            writer.value(_encodedImage)
        }
    }
}