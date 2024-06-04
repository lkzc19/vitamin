package org.example.upload.service

import org.example.upload.param.FileChunkParam

interface FileService {

    fun save(param: FileChunkParam): String
}