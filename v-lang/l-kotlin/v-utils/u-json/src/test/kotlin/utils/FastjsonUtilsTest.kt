package utils

import com.alibaba.fastjson2.TypeReference
import org.example.model.Foo4Fastjson
import org.example.model.Foo4Gson
import org.example.utils.FastjsonUtils
import kotlin.test.Test

class FastjsonUtilsTest {

    @Test
    fun testJson2Object1() {
        val json = "{\"name\":\"foo\",\"age\":18}"
        val foo = FastjsonUtils.json2Object(json, Foo4Fastjson::class.java)
        println(foo)

        val map = FastjsonUtils.json2Object(json, Map::class.java)
        map.forEach { (key, value) -> println("$key -> $value\t${value!!::class.simpleName}") }
    }

    @Test
    fun testJson2Object2() {
        val json = "{\"name\":\"foo\",\"age\":18}"
        val foo = FastjsonUtils.json2Object(json, object : TypeReference<Foo4Fastjson>() {})
        println(foo)

        val map = FastjsonUtils.json2Object(json, object : TypeReference<Map<String, String>>() {})
        map.forEach { (key, value) -> println("$key -> $value\t${value::class.simpleName}") }
    }

    @Test
    fun testObject2Json() {
        val foo = Foo4Gson(name = "foo", age = 18)
        val json = FastjsonUtils.object2Json(foo)
        println(json)
    }

}