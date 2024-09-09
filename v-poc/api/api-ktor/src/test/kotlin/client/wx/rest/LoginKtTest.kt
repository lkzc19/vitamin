package client.wx.rest

import kotlinx.coroutines.runBlocking
import org.example.client.wx.WxClient
import org.example.client.wx.rest.code2Session1
import org.junit.jupiter.api.Test

class LoginKtTest {

    @Test
    fun code2Session(): Unit = runBlocking {
        val client = WxClient()
        val response = client.code2Session1("xxx", "xxx")
        println(response)
    }
}