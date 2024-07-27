package org.example.param

import org.springframework.web.multipart.MultipartFile

data class FileChunkParam(
    val chunkNumber: Int,
    val totalChunks: Int,
    val chunkSize: Long,
    val currentChunkSize: Long,
    val totalSize: Int,
    val identifier: String,
    val relativePath: String,
    val filename: String,
    val file: MultipartFile,
)
