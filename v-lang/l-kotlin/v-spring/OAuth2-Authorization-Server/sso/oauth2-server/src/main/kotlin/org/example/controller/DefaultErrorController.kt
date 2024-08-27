package org.example.controller

import jakarta.servlet.RequestDispatcher
import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.RequestMapping

/**
 * @author Steve Riesenberg
 * @since 1.1
 */
@Controller
class DefaultErrorController : ErrorController {
    @RequestMapping("/error")
    fun handleError(model: Model, request: HttpServletRequest): String {
        val errorMessage = getErrorMessage(request)
        if (errorMessage.startsWith("[access_denied]")) {
            model.addAttribute("errorTitle", "Access Denied")
            model.addAttribute("errorMessage", "You have denied access.")
        } else {
            model.addAttribute("errorTitle", "Error")
            model.addAttribute("errorMessage", errorMessage)
        }
        return "error"
    }

    private fun getErrorMessage(request: HttpServletRequest): String {
        val errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE) as String?
        return if (StringUtils.hasText(errorMessage)) errorMessage ?: "" else ""
    }
}