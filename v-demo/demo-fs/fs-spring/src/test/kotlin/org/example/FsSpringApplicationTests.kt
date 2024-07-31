package org.example

import org.example.service.FsService
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
    lateinit var fsService: FsService

    @Test
    fun contextLoads() {
    }

    @Test
    fun test() {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val subList = list.subList(1, 5)
        println(subList)
    }

}
