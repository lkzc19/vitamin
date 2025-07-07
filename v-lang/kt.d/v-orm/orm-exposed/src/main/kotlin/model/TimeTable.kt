package org.example.model

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.timestamp

object TimeTable: IntIdTable() {
    val testTime = timestamp("testTime")
}