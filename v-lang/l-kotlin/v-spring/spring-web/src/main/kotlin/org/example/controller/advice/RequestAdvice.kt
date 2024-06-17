package org.example.controller.advice

import org.slf4j.LoggerFactory
import org.springframework.core.MethodParameter
import org.springframework.http.HttpInputMessage
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter
import java.lang.reflect.Type

@RestControllerAdvice
class RequestAdvice : RequestBodyAdviceAdapter() {

    private val logger = LoggerFactory.getLogger(RequestAdvice::class.java)

    override fun supports(
        methodParameter: MethodParameter,
        targetType: Type,
        converterType: Class<out HttpMessageConverter<*>>
    ): Boolean {
        logger.info("RequestAdvice supports...")
        return true
    }

    override fun beforeBodyRead(
        inputMessage: HttpInputMessage,
        parameter: MethodParameter,
        targetType: Type,
        converterType: Class<out HttpMessageConverter<*>>
    ): HttpInputMessage {
        logger.info("RequestAdvice beforeBodyRead...")
        return super.beforeBodyRead(inputMessage, parameter, targetType, converterType)
    }

}