package org.example.upload.controller

import jakarta.servlet.http.HttpServletResponse
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException


@RestController
class UploadController {

    @Value("\${vitamin.upload-path}")
    lateinit var uploadDir: String

    val prefix = "localhost:3000/file/"

    /**
     * 简单文件上传
     */
    @PostMapping("/upload.simple")
    fun handleSimpleUpload(@RequestParam("file") file: MultipartFile): String {
        if (file.isEmpty) {
            return "文件不能为空"
        }
        // 可以加一些如 目录是否存在的判断 | 文件是否已存在 | 文件名md5&映射
        file.transferTo(File(uploadDir + file.originalFilename)) // 将文件保存到指定路径
        return prefix + file.originalFilename
    }

    @PostMapping("/upload.part")
    fun handlePartUpload(@RequestParam("file") file: MultipartFile): String {

        TODO()
    }

    @GetMapping("/download/{filename}")
    @Throws(IOException::class)
    fun download(@PathVariable("filename") filename: String, response: HttpServletResponse): String {
        val file = File(uploadDir + filename)
        if (!file.exists()) {
            return "文件不存在"
        }
        val outputStream = response.outputStream
        outputStream.write(file.readBytes()) // 数组是一个字节数组，也就是文件的字节流数组
        outputStream.flush()
        outputStream.close()
        return "OK"
    }
}