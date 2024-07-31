package org.example.controller.advice

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.BizException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {
    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(BizException::class)
    fun handleBizException(e: BizException): ResponseEntity<BizErrorResult> {
        logger.error(e) { "ExceptionAdvice ${e.message}" }
        val bizErrorResult = BizErrorResult(message = e.message)
        return ResponseEntity(bizErrorResult, e.httpStatus)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<BizErrorResult> {
        val bizErrorResult = BizErrorResult()
        logger.error(e) { "ExceptionAdvice" }
        return ResponseEntity(bizErrorResult, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

data class BizErrorResult(val message: String? = "服务器错误")