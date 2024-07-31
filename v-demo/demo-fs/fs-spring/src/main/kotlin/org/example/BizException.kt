package org.example

import org.springframework.http.HttpStatus

open class BizException(
    override val message: String = "服务器错误",
    open val httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
): RuntimeException()