package utils

import com.fasterxml.jackson.core.type.TypeReference
import org.example.model.Foo4Jackson
import org.example.utils.JacksonUtils
import kotlin.test.Test

class JacksonUtilsTest {

    @Test
    fun testJson2Object1() {
        val json = "{\"name\":\"foo\",\"age\":18}"
        val foo = JacksonUtils.json2Object(json, Foo4Jackson::class.java)
        println(foo)

        val map = JacksonUtils.json2Object(json, Map::class.java)
        map.forEach { (key, value) -> println("$key -> $value\t${value!!::class.simpleName}") }
    }

    @Test
    fun testJson2Object2() {
        val json = "{\"name\":\"foo\",\"age\":18}"
        val foo = JacksonUtils.json2Object(json, object : TypeReference<Foo4Jackson>() {})
        println(foo)

        val map = JacksonUtils.json2Object(json, object : TypeReference<Map<String, String>>() {})
        map.forEach { (key, value) -> println("$key -> $value\t${value::class.simpleName}") }
    }

    @Test
    fun testObject2Json() {
        val foo = Foo4Jackson(name = "foo", age = 18)
        val json = JacksonUtils.object2Json(foo)
        println(json)
    }

}