package org.example.upload.service

import org.example.upload.param.FileChunkParam
import org.springframework.web.multipart.MultipartFile

interface FileService {

    fun save(param: FileChunkParam): String
    
    fun save(file: MultipartFile): String
}