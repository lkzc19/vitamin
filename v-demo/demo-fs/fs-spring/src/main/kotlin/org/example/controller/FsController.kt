package org.example.controller

import jakarta.servlet.http.HttpServletResponse
import org.apache.juli.logging.LogFactory
import org.example.BizException
import org.example.param.FileChunkParam
import org.example.param.PageParam
import org.example.service.FsService
import org.example.vo.FileVo
import org.example.vo.GetInfoVo
import org.example.vo.PageVo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.net.URLEncoder


@RestController
class FsController {

    private val logger = LogFactory.getLog(FsController::class.java)

    @Value("\${vitamin.fs-dir}")
    lateinit var fsDir: String

    @Autowired
    lateinit var fsService: FsService

    /**
     * 获取基本信息
     */
    @GetMapping("/info")
    fun getInfo(): ResponseEntity<GetInfoVo> {
        val vo = fsService.getInfo()
        return ResponseEntity.ok(vo)
    }

    /**
     * 获取指定目录下的所有目录及文件
     */
    @GetMapping("/file.page")
    fun pageFile(
        @RequestParam path: String,
        @RequestParam(defaultValue = "1") pageC: Int,   // current 当前页
        @RequestParam(defaultValue = "10") pageS: Int,   // size 每页条数
    ): ResponseEntity<PageVo<FileVo>> {
        val vo = fsService.pageFile(path, PageParam(pageC, pageS))
        return ResponseEntity.ok(vo)
    }

    /**
     * 获取指定目录下的所有目录及文件
     */
    @GetMapping("/file.list")
    fun listFile(@RequestParam path: String): ResponseEntity<List<FileVo>> {
        val vo = fsService.listFile(path)
        return ResponseEntity.ok(vo)
    }

    /**
     * 创建目录
     */
    @PutMapping("/dir")
    fun mkDir(
        @RequestParam path: String,
        @RequestParam name: String
    ): ResponseEntity<String> {
        if (path.isBlank() || name.isBlank()) {
            throw BizException(message = "[path]或[name]不可为空", httpStatus = HttpStatus.BAD_REQUEST)
        }
        if (!path.endsWith(File.separator)) {
            throw BizException(message = "[path: $path]必须以[${File.separator}]结尾", httpStatus = HttpStatus.BAD_REQUEST)
        }
        val vo = fsService.mkdir(path, name)
        return ResponseEntity.ok(vo)
    }

    /**
     * 下载文件接口 通过此接口下载可以统计信息
     * 或者使用 Spring提供的 /file/ 接口下载
     */
    @GetMapping("/download")
    @Throws(IOException::class)
    fun download(@RequestParam("fullFilename") fullFilename: String, response: HttpServletResponse) {
        // Content-Disposition 是 HTTP 响应头部的一个字段，它定义了获取的资源如何处理。这个字段通常用于下载操作，告诉浏览器如何处理响应的内容。
        // Content-Disposition 主要有两个值:
        //   inline: 这意味着资源应该直接在浏览器中显示，如果可能的话。
        //      例如，如果资源是一个图像或 PDF 文档，大多数浏览器都能够直接在浏览器窗口中显示这些类型的文件。
        //   attachment: 这意味着资源应该被下载和保存到本地，而不是直接在浏览器中显示。
        //
        //   Content-Disposition 还可以包含一个 filename 参数，这个参数可以指定一个默认的文件名，当用户保存资源时，浏览器会使用这个文件名。
        //      例如，Content-Disposition: attachment; filename="filename.jpg" 这个头部会告诉浏览器下载资源并将其保存为 "filename.jpg"。

        val file = File(fsDir + File.separator + fullFilename)
        if (!file.exists()) {
            throw BizException(message = "[${file.name}]不存在", httpStatus = HttpStatus.BAD_REQUEST)
        }

        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.name, "UTF-8"))
        val outputStream = response.outputStream
        outputStream.write(File(fsDir + File.separator + fullFilename).readBytes()) // 数组是一个字节数组，也就是文件的字节流数组
        outputStream.flush()
        outputStream.close()
    }

    /**
     * 简单文件上传
     */
    @PostMapping("/upload.simple")
    fun handleSimpleUpload(
        @RequestParam("path") path: String,
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<String> {
        if (path.isBlank() || file.isEmpty) {
            throw BizException(message = "[path]或[file]不可为空", httpStatus = HttpStatus.BAD_REQUEST)
        }
        if (!path.endsWith(File.separator)) {
            throw BizException(message = "[path: $path]必须以[${File.separator}]结尾", httpStatus = HttpStatus.BAD_REQUEST)
        }
        val vo = fsService.upload(path, file)
        return ResponseEntity.ok(vo)
    }

    /**
     * 分块上传
     */
    @PostMapping("/upload")
    fun handlePostUpload(@ModelAttribute param: FileChunkParam): String {
        logger.info("handlePostUpload: ${param.identifier}")

        val dir = File(fsDir)
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return "目录创建失败"
            }
        }
        
        fsService.save(param)
        return param.identifier
    }
}