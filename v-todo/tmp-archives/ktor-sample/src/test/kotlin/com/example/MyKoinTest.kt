package com.example

import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject

class MyKoinTest: KoinTest {

    private val componentB : ComponentB by inject()

    @Test
    fun `should inject my components`() {
        startKoin{
            modules(
                module {
                    factory { ComponentA() }
                    single { ComponentB(get()) }
                })
        }

        // factory -> 多例    single -> 单例
        val componentA = get<ComponentA>()
        println(componentA === componentB.a)
    }
}

class ComponentA
class ComponentB(val a: ComponentA)