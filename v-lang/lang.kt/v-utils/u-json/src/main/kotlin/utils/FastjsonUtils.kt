package org.example.utils

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.TypeReference

object FastjsonUtils {

    fun <T> json2Object(json: String, clazz: Class<T>): T = JSON.parseObject(json, clazz)

    fun <T> json2Object(json: String, typeRef: TypeReference<T>): T = JSON.parseObject(json, typeRef)

    fun object2Json(obj: Any): String = JSON.toJSONString(obj)
}