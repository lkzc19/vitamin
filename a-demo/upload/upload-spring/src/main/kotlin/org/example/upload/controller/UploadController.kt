package org.example.upload.controller

import jakarta.servlet.http.HttpServletResponse
import org.apache.commons.io.FileUtils
import org.example.upload.param.FileChunkParam
import org.example.upload.service.FileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.net.URLEncoder


@RestController
class UploadController {

    @Value("\${vitamin.upload-path}")
    lateinit var uploadDir: String

    @Autowired
    lateinit var fileService: FileService

    /**
     * 分片上传
     */
    @PostMapping("/upload")
    fun handleUpload(@ModelAttribute param: FileChunkParam): String {
        val dir = File(uploadDir)
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return "目录创建失败"
            }
        }
        
        fileService.save(param)
        println(param.identifier)
        return "foo"
    }

    /**
     * 简单文件上传
     */
    @PostMapping("/upload.simple")
    fun handleSimpleUpload(@RequestParam("file") file: MultipartFile): String {
        val dir = File(uploadDir)
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return "目录创建失败"
            }
        }
        return fileService.save(file)
    }

    @GetMapping("/download/{filename}")
    @Throws(IOException::class)
    fun download(@PathVariable("filename") filename: String, response: HttpServletResponse): String {
        // Content-Disposition 是 HTTP 响应头部的一个字段，它定义了获取的资源如何处理。这个字段通常用于下载操作，告诉浏览器如何处理响应的内容。
        // Content-Disposition 主要有两个值:
        //   inline: 这意味着资源应该直接在浏览器中显示，如果可能的话。
        //      例如，如果资源是一个图像或 PDF 文档，大多数浏览器都能够直接在浏览器窗口中显示这些类型的文件。
        //   attachment: 这意味着资源应该被下载和保存到本地，而不是直接在浏览器中显示。
        //
        //   Content-Disposition 还可以包含一个 filename 参数，这个参数可以指定一个默认的文件名，当用户保存资源时，浏览器会使用这个文件名。
        //      例如，Content-Disposition: attachment; filename="filename.jpg" 这个头部会告诉浏览器下载资源并将其保存为 "filename.jpg"。
        
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"))
        val outputStream = response.outputStream
        outputStream.write(File(uploadDir + filename).readBytes()) // 数组是一个字节数组，也就是文件的字节流数组
        outputStream.flush()
        outputStream.close()
        return "OK"
    }
}