package org.example.exception

import org.springframework.http.HttpStatus

open class BizException(
    open val errorType: BizErrorTypeEnum = BizErrorTypeEnum.SERVER_ERROR,
    override val message: String = "服务器错误",
    open val httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
): RuntimeException()

enum class BizErrorTypeEnum {
    SERVER_ERROR,
    UNAUTHORIZED,
    UNREGISTERED,
    VERIFICATION
}