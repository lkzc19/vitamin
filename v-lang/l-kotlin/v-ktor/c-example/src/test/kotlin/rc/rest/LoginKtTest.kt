package rc.rest

import kotlinx.coroutines.runBlocking
import org.example.rc.RcClient
import org.example.rc.rest.login
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class LoginKtTest {

    @Test
    fun login() = runBlocking {
        val client = RcClient()
        val response = client.login("CKMRO.Bot", "11111")
        println(response)
    }
}