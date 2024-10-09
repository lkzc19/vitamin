package org.example.utils

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.zip.GZIPInputStream


object GzipUtils {

    fun decompress(data: String): String {
        val decodedData = Base64.getDecoder().decode(data)
        val inputStream = ByteArrayInputStream(decodedData)
        val gzipInputStream = GZIPInputStream(inputStream)
        val outputStream = ByteArrayOutputStream()
        gzipInputStream.copyTo(outputStream)
        return outputStream.toString(Charsets.UTF_8)
    }
}