package org.example.service

import org.example.param.FileChunkParam
import org.example.vo.FileVo
import org.springframework.web.multipart.MultipartFile
import java.io.File

interface FsService {

    fun save(param: FileChunkParam): Boolean

    fun save(finalFilename: String, file: MultipartFile): String

    fun getDS(file: File, items: MutableList<FileVo>)
}