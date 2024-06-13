package org.example.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper


object JsonUtils {
	
	private val objectMapper = ObjectMapper()
	
	fun <T> json2Object(json: String, clazz: Class<T>): T = objectMapper.readValue(json, clazz)
	
	fun <T> jsonToObject(json: String?, typeRef: TypeReference<T>): T = objectMapper.readValue(json, typeRef)
	
	fun object2Json(obj: Any): String = objectMapper.writeValueAsString(obj)
}