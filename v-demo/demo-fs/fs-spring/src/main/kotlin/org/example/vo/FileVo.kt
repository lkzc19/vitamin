package org.example.vo

data class FileVo(
    val name: String,
    val ext: String? = null,
    val isDir: Boolean = false,
    val size: Long? = null, // 字节
)
