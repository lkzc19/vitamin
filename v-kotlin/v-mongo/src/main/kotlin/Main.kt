import com.mongodb.kotlin.client.coroutine.MongoClient
import dao.AlbumDao
import dao.FoobarDao
import model.Bar
import model.Foo
import model.Foobar
import kotlin.system.measureTimeMillis

val mongoClient = MongoClient.create("mongodb://vitamin:vitamin@127.0.0.1:23017/")
val database = mongoClient.getDatabase("vitamin")

val albumDao = AlbumDao(database)
val foobarDao = FoobarDao(database)

fun main() {
  val time = measureTimeMillis {
    testBigData4Page()
  }
  println("time: $time")
  mongoClient.close()
}

fun testBigData4Insert() {
  val foobarList = mutableListOf<Foobar>()
  for (i in 500001..1000000) {
    val barList = mutableListOf<Bar>()
    for (j in 0..100) {
      barList.add(Bar(barId = "BAR-$j"))
    }
    Foobar(foo = Foo("$i"), barList = barList).let { foobarList.add(it) }
  }
  
  
  foobarDao.createBatch(foobarList)
}

fun testBigData4Count() {
  println("count: ${foobarDao.count()}")
}

fun testBigData4Page() {
  val page = foobarDao.page()
  page.forEach { println(it) }
  println("count: " + page.size)
}

fun test() {
//  albumDao.create()
//  albumDao.find()
  albumDao.page()
}

