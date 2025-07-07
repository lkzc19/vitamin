package rc.rest

import kotlinx.coroutines.runBlocking
import org.example.rc.RcClient
import org.example.rc.rest.login
import org.example.rc.rest.postMessage
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ChatKtTest {

    @Test
    fun postMessage() = runBlocking {
        val client = RcClient()
        val loginResponse = client.login("CKMRO.Bot", "111111")
        val userId = loginResponse.data!!.userId
        val authToken = loginResponse.data!!.authToken
        client.userId = userId
        client.authToken = authToken
        val postMessageResponse = client.postMessage("13559440240", "妮可妮可妮")
        println(postMessageResponse)
    }
}