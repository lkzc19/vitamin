package org.example.upload.service.impl

import org.example.upload.param.FileChunkParam
import org.example.upload.service.FileService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.RandomAccessFile

@Service
class FileServiceImpl : FileService {
    
    private val prefix = "localhost:3000/file/"
    
    private val defaultChunkSize = 1024 * 1024 * 2L

    @Value("\${vitamin.upload-path}")
    lateinit var uploadDir: String

    override fun save(param: FileChunkParam): String {
        val fullFilename = uploadDir + File.separator + param.filename

//        if (param.totalChunks == 1) {
//            TODO()
//        }


        return "todo"
    }
    
    override fun save(file: MultipartFile): String {
        // 可以加一些如 目录是否存在的判断 | 文件是否已存在 | 文件名md5&映射
        file.transferTo(File(uploadDir + File.separator + file.originalFilename)) // 将文件保存到指定路径
        return prefix + file.originalFilename
    }
    
    private fun saveFileByRandomAccessFile(fullFilename: String, param: FileChunkParam): Boolean {
        RandomAccessFile(fullFilename, "rw").use {
            val chunkSize = if (param.chunkSize == 0L) {
                defaultChunkSize
            } else {
                param.chunkSize
            }
            val offset = chunkSize * (param.chunkSize - 1)
            it.seek(offset)
            it.write(param.file.bytes)
        }
        return true
    }
}