package org.example.service

import org.example.param.FileChunkParam
import org.example.param.PageParam
import org.example.vo.FileVo
import org.example.vo.GetInfoVo
import org.example.vo.PageVo
import org.springframework.web.multipart.MultipartFile

interface FsService {

    fun getInfo(): GetInfoVo

    fun listFile(path: String, pageParam: PageParam): PageVo<FileVo>

    fun save(param: FileChunkParam): Boolean

    fun save(finalFilename: String, file: MultipartFile): String
}