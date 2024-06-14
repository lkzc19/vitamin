import model.City
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
  val database = Database.connect(
    url = "jdbc:postgresql://localhost:3432/vitamin",
    driver = "org.postgresql.Driver",
    user = "vitamin",
    password = "vitamin"
  )
  
  transaction {
    SchemaUtils.create(City)
    
    // insert new city. SQL: INSERT INTO Cities (name) VALUES ('St. Petersburg')
    val cityId = City.insert {
      it[name] = "三明"
    } get City.id
    
    println("cityId: $cityId")
    
    // 'select *' SQL: SELECT Cities.id, Cities.name FROM Cities
    println("City: ${City.selectAll()}")
  }
}