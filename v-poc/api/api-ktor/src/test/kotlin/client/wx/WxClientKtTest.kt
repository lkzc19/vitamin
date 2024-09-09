package client.wx

import org.example.client.wx.WxClient
import org.example.client.wx.info
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class WxClientKtTest {

    @Test
    fun info() {
        val client = WxClient()
        client.info()
    }
}