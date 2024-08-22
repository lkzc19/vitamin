package org.example.utils

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import java.io.ByteArrayOutputStream
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object QRCodeUtils {

    private val qrCodeWriter = QRCodeWriter()

    @OptIn(ExperimentalEncodingApi::class)
    fun genQRCode(str: String): String {
        val bitMatrix = qrCodeWriter.encode(str, BarcodeFormat.QR_CODE, 100, 100)
        val qrcode = ByteArrayOutputStream().use {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", it)
            // 返回Base64编码的PNG数据
            return@use Base64.encode(it.toByteArray())
        }
        return qrcode
    }
}