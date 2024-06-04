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

    val prefix = "localhost:3000/file/"

    @Autowired
    lateinit var fileService: FileService

    /**
     * 分片上传
     */
    @PostMapping("/upload")
    fun handlePartUpload(@ModelAttribute param: FileChunkParam): String {

        fileService.save(param)
        println(param.identifier)
        return "foo"
    }

    /**
     * 简单文件上传
     */
    @PostMapping("/upload.simple")
    fun handleSimpleUpload(@RequestParam("file") file: MultipartFile): String {
        // 在 Spring Boot 中，你可以在 application.properties 或 application.yml 文件中配置文件上传的最大大小。
        //  这是通过设置 spring.servlet.multipart.max-file-size 和 spring.servlet.multipart.max-request-size 属性来实现的。
        // spring.servlet.multipart.max-file-size: 设置单个文件的最大大小。如果上传的文件超过这个大小，将会抛出异常。
        // spring.servlet.multipart.max-request-size: 设置整个请求的最大大小。这包括所有的文件和其他表单数据。如果整个请求的大小超过这个值，将会抛出异常。
        // spring.servlet.multipart.file-size-threshold: 这个属性用于设置写入磁盘的文件的大小阈值。如果上传的文件大小超过这个阈值，那么文件将会被写入到磁盘，而不是保存在内存中。这可以防止大文件上传消耗过多的内存。
        // spring.servlet.multipart.location: 这个属性用于设置临时文件的存储位置。当文件大小超过 file-size-threshold 时，文件将会被写入到这个位置。

        if (file.isEmpty) {
            return "文件不能为空"
        }
        // 可以加一些如 目录是否存在的判断 | 文件是否已存在 | 文件名md5&映射
        file.transferTo(File(uploadDir + file.originalFilename)) // 将文件保存到指定路径
        return prefix + file.originalFilename
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

        val file = File(uploadDir + filename)
        if (!file.exists()) {
            return "文件不存在"
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"))
        val outputStream = response.outputStream
        outputStream.write(file.readBytes()) // 数组是一个字节数组，也就是文件的字节流数组
        outputStream.flush()
        outputStream.close()
        return "OK"
    }
}