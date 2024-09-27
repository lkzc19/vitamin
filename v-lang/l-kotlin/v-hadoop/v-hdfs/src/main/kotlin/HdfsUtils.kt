package org.example

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileStatus
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import java.io.IOException

object HdfsUtils {

    // 创建配置对象
    private val config: Configuration = Configuration()
    private val fs: FileSystem

    init {
        // 设置HDFS的URI
        config.set("fs.defaultFS", "hdfs://localhost:9870")
        config.set("ipc.max.response.size", "200000000")
        config.set("io.file.buffer.size", "131072")
        // 创建文件系统对象
        fs = FileSystem.get(config)
    }

    @Throws(IOException::class)
    fun uploadFile(src: String?, dst: String?) {
        val srcPath = Path(src)
        val dstPath = Path(dst)
        // 调用文件系统的文件复制函数,前面参数是指是否删除原文件,true为删除,默认为false
        fs.copyFromLocalFile(false, srcPath, dstPath)
        fs.close()
    }
}