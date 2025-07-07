package utils

import com.google.gson.reflect.TypeToken
import org.example.model.Foo4Gson
import org.example.utils.GsonUtils
import kotlin.test.Test

class GsonUtilsTest {

    @Test
    fun testJson2Object1() {
        val json = "{\"name\":\"foo\",\"age\":18}"
        val foo = GsonUtils.json2Object(json, Foo4Gson::class.java)
        println(foo)

        val map = GsonUtils.json2Object(json, Map::class.java)
        map.forEach { (key, value) -> println("$key -> $value\t${value!!::class.simpleName}") }
    }

    @Test
    fun testJson2Object2() {
        val json = "{\"name\":\"foo\",\"age\":18}"
        val foo = GsonUtils.json2Object(json, object : TypeToken<Foo4Gson>() {})
        println(foo)

        val map = GsonUtils.json2Object(json, object : TypeToken<Map<String, String>>() {})
        map.forEach { (key, value) -> println("$key -> $value\t${value::class.simpleName}") }
    }

    @Test
    fun testObject2Json() {
        val foo = Foo4Gson(name = "foo", age = 18)
        val json = GsonUtils.object2Json(foo)
        println(json)
    }

}