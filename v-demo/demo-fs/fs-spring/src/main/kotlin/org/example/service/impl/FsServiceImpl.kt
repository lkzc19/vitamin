package org.example.service.impl

import org.example.param.FileChunkParam
import org.example.service.FsService
import org.example.vo.FileVo
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.RandomAccessFile
import java.nio.channels.FileChannel


@Service
class FsServiceImpl : FsService {

    private val prefix = "localhost:3000/file/"

    private val defaultChunkSize = 1024 * 1024 * 2L

    @Value("\${vitamin.fs-dir}")
    lateinit var fsDir: String

    override fun save(param: FileChunkParam): Boolean {
        val fullFilename = fsDir + File.separator + param.filename

        if (param.totalChunks == 1) {
            this.save(fullFilename, param.file)
            return true
        }

        return saveFileByRandomAccessFile(fullFilename, param)
    }

    override fun save(finalFilename: String, file: MultipartFile): String {
        // 可以加一些如 目录是否存在的判断 | 文件是否已存在 | 文件名md5&映射
        file.transferTo(File(finalFilename)) // 将文件保存到指定路径
        return prefix + file.originalFilename
    }

    private fun saveFileByRandomAccessFile(finalFilename: String, param: FileChunkParam): Boolean {
        RandomAccessFile(finalFilename, "rw").use {
            val chunkSize = if (param.chunkSize == 0L) {
                defaultChunkSize
            } else {
                param.chunkSize
            }
            val offset = chunkSize * (param.chunkNumber - 1)
            it.seek(offset)
            it.write(param.file.bytes)
        }
        return true
    }

    private fun saveFileByMappedByteBuffer(finalFilename: String, param: FileChunkParam): Boolean {
        RandomAccessFile(finalFilename, "rw").use { raf ->
            raf.channel.use {
                val chunkSize = if (param.chunkSize == 0L) {
                    defaultChunkSize
                } else {
                    param.chunkSize
                }
                val offset = chunkSize * (param.chunkNumber - 1)
                val fileBytes = param.file.bytes
                val mappedByteBuffer = it.map(FileChannel.MapMode.READ_WRITE, offset, fileBytes.size.toLong())
                mappedByteBuffer.put(fileBytes)
                mappedByteBuffer.force()
                // TODO 即使清除mappedByteBuffer中的数据
            }
        }
        return true
    }

    override fun getDS(file: File, items: MutableList<FileVo>) {
        if (file.isDirectory) {
            val children = mutableListOf<FileVo>()
            file.listFiles()?.forEach { getDS(it, children) }
            items.add(FileVo(name = file.name, isDir = true, items = children))
        } else {
            items.add(FileVo(name = file.name, ext = file.extension))
        }
    }

}