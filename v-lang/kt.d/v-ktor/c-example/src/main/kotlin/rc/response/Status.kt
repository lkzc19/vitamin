package org.example.rc.response

import java.util.*

enum class Status {
    SUCCESS, ERROR;

    override fun toString() = name.lowercase(Locale.getDefault())
}