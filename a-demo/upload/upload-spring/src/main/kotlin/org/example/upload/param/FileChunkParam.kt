package org.example.upload.param

import org.springframework.web.multipart.MultipartFile

data class FileChunkParam(
    val chunkNumber: Int,
    val totalChunks: Int,
    val chunkSize: Long,
    val currentChunkSize: Int,
    val totalSize: Int,
    val identifier: String,
    val relativePath: String,
    val filename: String,
    val file: MultipartFile,
)
