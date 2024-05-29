package org.example.upload.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException

@RestController
class UploadController {

    @Value("\${vitamin.upload-path}")
    lateinit var uploadDir: String

    /**
     * 简单文件上传
     */
    @PostMapping("/upload.simple") // 定义一个处理文件上传的POST请求映射
    fun handleFileUpload(@RequestParam("file") file: MultipartFile): String { // 接收名为"file"的文件参数
        try {
            file.transferTo(File(uploadDir + file.originalFilename)) // 将文件保存到指定路径
            return "OK"
        } catch (e: IOException) {
            e.printStackTrace()
            return "不OK"
        }
    }

}