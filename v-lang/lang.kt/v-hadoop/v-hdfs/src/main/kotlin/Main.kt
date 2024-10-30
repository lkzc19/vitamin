package org.example

fun main() {
    // 本地文件路径
    val src = "C:\\Users\\lkzc19\\Desktop\\hua_nobg_512.gif"
    // HDFS目标路径
    val dst = "/vitamin/test/hua_nobg_512.gif"

    HdfsUtils.uploadFile(src, dst)
}