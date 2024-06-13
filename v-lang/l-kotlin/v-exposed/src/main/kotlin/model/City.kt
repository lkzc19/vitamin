package model

import org.jetbrains.exposed.dao.id.IntIdTable

object City: IntIdTable() {
  val name = varchar("name", 50)
}