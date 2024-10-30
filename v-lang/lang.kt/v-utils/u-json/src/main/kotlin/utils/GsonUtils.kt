package org.example.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object GsonUtils {

    private val gson = Gson()

    fun <T> json2Object(json: String, clazz: Class<T>): T = gson.fromJson(json, clazz)

    fun <T> json2Object(json: String, typeToken: TypeToken<T>): T = gson.fromJson(json, typeToken)

    fun object2Json(obj: Any): String = gson.toJson(obj)
}