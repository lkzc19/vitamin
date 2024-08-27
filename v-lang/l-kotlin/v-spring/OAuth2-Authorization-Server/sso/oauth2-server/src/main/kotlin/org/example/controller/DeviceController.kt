package org.example.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * @author Steve Riesenberg
 * @since 1.1
 */
@Controller
class DeviceController {
    @GetMapping("/activate")
    fun activate(@RequestParam(value = "user_code", required = false) userCode: String?): String {
        if (userCode != null) {
            return "redirect:/oauth2/device_verification?user_code=$userCode"
        }
        return "device-activate"
    }

    @GetMapping("/activated")
    fun activated(): String {
        return "device-activated"
    }

    @GetMapping(value = ["/"], params = ["success"])
    fun success(): String {
        return "device-activated"
    }
}