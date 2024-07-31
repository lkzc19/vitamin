package org.example.vo

data class PageVo<T>(
    val pageC: Int,
    val pageS: Int,
    val pageT: Int,
    val items: List<T> = emptyList(),
)