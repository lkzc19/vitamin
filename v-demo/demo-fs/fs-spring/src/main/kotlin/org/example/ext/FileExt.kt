package org.example.ext

import java.io.File

fun File.virtualPath(realPath: String) =
    this.absolutePath.replace(realPath, "") + if (this.isDirectory) "/" else ""

