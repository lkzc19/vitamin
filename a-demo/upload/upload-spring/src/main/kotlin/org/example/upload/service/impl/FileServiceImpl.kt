package org.example.upload.service.impl

import org.example.upload.param.FileChunkParam
import org.example.upload.service.FileService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.io.RandomAccessFile

@Service
class FileServiceImpl : FileService {

    private val defaultChunkSize = 1024 * 1024 * 2L

    @Value("\${vitamin.upload-path}")
    lateinit var uploadDir: String

    override fun save(param: FileChunkParam): String {

        val dir = File(uploadDir)
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return "目录创建失败"
            }
        }

        val fullFilename = uploadDir + File.separator + param.filename

//        if (param.totalChunks == 1) {
//            TODO()
//        }


        return "todo"
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