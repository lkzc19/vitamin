package com.example.client

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class VitaminClientTest {

    private val client = VitaminClient()

    @Test
    fun ping() = runBlocking {
        client.ping()
    }

    @Test
    fun log() = runBlocking {
        client.log()
    }

    @Test
    fun `401`() = runBlocking {
        client.`401`()
    }

    @Test
    fun retry401() = runBlocking {
        client.retry401()
    }
}