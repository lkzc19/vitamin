package org.example.service.impl

import org.example.BizException
import org.example.IGNORE
import org.example.ext.virtualPath
import org.example.param.FileChunkParam
import org.example.param.PageParam
import org.example.service.FsService
import org.example.vo.FileVo
import org.example.vo.GetInfoVo
import org.example.vo.PageVo
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.RandomAccessFile
import java.nio.channels.FileChannel
import java.util.*


@Service
class FsServiceImpl : FsService {

    private val defaultChunkSize = 1024 * 1024 * 2L

    @Value("\${vitamin.fs-dir}")
    lateinit var fsDir: String

    @Value("\${vitamin.prefix}")
    lateinit var prefix: String

    override fun getInfo(): GetInfoVo {
        var fileCount = 0
        val queue = LinkedList<File>()
        queue.add(File(fsDir))
        while (queue.isNotEmpty()) {
            queue.remove().listFiles()?.forEach {
                if (it.isDirectory) {
                    queue.add(it)
                } else {
                    // 忽略文件不计
                    if (!IGNORE.contains(it.name)) {
                        fileCount++
                    }
                }
            }
        }
        return GetInfoVo(fileCount = fileCount)
    }

    override fun pageFile(path: String, pageParam: PageParam): PageVo<FileVo> {
        val itemsBySort = listFile(path)

        val subList = if (pageParam.offset >= itemsBySort.size) {
            emptyList()
        } else if (pageParam.offset + pageParam.limit > itemsBySort.size) {
            itemsBySort.subList(pageParam.offset, itemsBySort.size)
        } else {
            itemsBySort.subList(pageParam.offset, pageParam.offset + pageParam.limit)
        }
        return PageVo(
            pageC = pageParam.pageC,
            pageS = pageParam.pageS,
            pageT = pageParam.pageT(itemsBySort.size),
            items = subList
        )
    }

    override fun listFile(path: String): List<FileVo> {
        val file = File(fsDir + path)
        if (!file.isDirectory) {
            throw BizException(message = "[path]不是目录", httpStatus = HttpStatus.NOT_FOUND)
        }
        val items = file.listFiles()?.mapNotNull {
            if (IGNORE.contains(it.name)) {
                return@mapNotNull null
            }
            FileVo(
                name = it.name,
                ext = it.extension,
                isDir = it.isDirectory,
                size = it.length(),
                fullPath = it.virtualPath(fsDir)
            )
        } ?: emptyList()
        // 排序 先目录 在文件名
        val itemsBySort = items
            .sortedWith(compareBy<FileVo> { !it.isDir }.thenBy { it.name })
            .toMutableList()
        // 手动添加上一级目录
        if (path != "/") {
            itemsBySort.add(0, FileVo(
                name = "..",
                ext = "",
                isDir = true,
                size = 4096,
                fullPath = "$path../"
            ))
        }
        return itemsBySort
    }

    override fun searchFile(keyword: String): List<FileVo> {
        return listAll().filter { it.name.contains(keyword, true) }
    }

    private fun listAll(): List<FileVo> {
        val items = mutableListOf<FileVo>()

        val queue = ArrayDeque<File>()
        queue.add(File("$fsDir/"))

        while (queue.isNotEmpty()) {
            val dir = queue.remove()
            dir.listFiles()?.forEach {
                if (IGNORE.contains(it.name)) {
                    return@forEach
                }
                items.add(FileVo(
                    name = it.name,
                    ext = it.extension,
                    isDir = it.isDirectory,
                    size = it.length(),
                    fullPath = it.virtualPath(fsDir)
                ))
                if (it.isDirectory) {
                    queue.add(it)
                }
            }
        }
        return items
    }

    override fun mkdir(path: String, name: String): String {
        if (!File(fsDir + path).exists()) {
            throw BizException(message = "[path: ${path}]目录不存在", httpStatus = HttpStatus.BAD_REQUEST)
        }
        val dir = File(fsDir + path + name)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return path + name
    }

    override fun upload(path: String, file: MultipartFile): String {
        if (!File(fsDir + path).exists()) {
            throw BizException(message = "[$path]目录不存在", httpStatus = HttpStatus.BAD_REQUEST)
        }
        val dest = File(fsDir + path + file.originalFilename)
        file.transferTo(dest)
        return prefix + path + file.originalFilename
    }

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
        return file.originalFilename ?: ""
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

//    override fun getDS(file: File, items: MutableList<FileVo>) {
//        if (file.isDirectory) {
//            val children = mutableListOf<FileVo>()
//            file.listFile()?.forEach { getDS(it, children) }
//            items.add(FileVo(name = file.name, isDir = true, items = children))
//        } else {
//            items.add(FileVo(name = file.name, ext = file.extension))
//        }
//    }

}