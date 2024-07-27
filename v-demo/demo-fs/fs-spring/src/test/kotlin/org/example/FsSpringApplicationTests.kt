package org.example

import org.example.service.FileService
import org.example.vo.FileVo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import java.io.File

@SpringBootTest
class FsSpringApplicationTests {

    @Value("\${vitamin.fs-dir}")
    lateinit var fsDir: String

    @Autowired
    lateinit var fileService: FileService

    @Test
    fun contextLoads() {
    }

    @Test
    fun testListFiles() {
        val directory = File(fsDir)
        val items = mutableListOf<FileVo>()

        if (directory.exists() && directory.isDirectory) {
            fileService.getDS(directory, items)
        } else {
            println("Directory not found.")
        }

        items.forEach { println(it) }
    }

}
