package org.example.upload

import org.junit.jupiter.api.Test
import java.io.RandomAccessFile
import java.nio.charset.StandardCharsets


class RandomAccessFileTest {

    private val filePath = "C:\\Users\\25786\\Projects\\vitamin\\tmp\\foo.txt"

    @Test
    fun testWrite() {
        val e1 = Employee("玉米投手", 23)
        val e2 = Employee("豌豆射手", 24)
        val e3 = Employee("杨桃大帝", 25)
        val ra = RandomAccessFile(filePath, "rw")
        ra.setLength(0)
        ra.write(e1.name.toByteArray(StandardCharsets.UTF_8))//防止写入文件乱码
        ra.writeInt(e1.age)
        ra.write(e2.name.toByteArray(StandardCharsets.UTF_8))
        ra.writeInt(e2.age)
        ra.write(e3.name.toByteArray(StandardCharsets.UTF_8))
        ra.writeInt(e3.age)
        ra.close()
    }

    @Test
    fun testRead() {
        val raf = RandomAccessFile(filePath, "r")
        val len = 8
        raf.skipBytes(12) //跳过第一个员工的信息，其姓名8字节，年龄4字节
        println("第二个员工信息：")
        var str = ""
        for (i in 0 until len) {
            str += Char(raf.readByte().toUShort())
        }
        println("name:$str")
        println("age:" + raf.readInt())
        println("第一个员工信息：")
        raf.seek(0) //将文件指针移动到文件开始位置
        str = ""
        for (i in 0 until len) {
            str += Char(raf.readByte().toUShort())
        }
        println("name:$str")
        println("age:" + raf.readInt())
        println("第三个员工信息：")
        raf.skipBytes(12) //跳过第二个员工的信息
        str = ""
        for (i in 0 until len) {
            str += Char(raf.readByte().toUShort())
        }
        println("name:$str")
        println("age:" + raf.readInt())
        raf.close()
    }
}

data class Employee(
    val name: String,
    val age: Int
)