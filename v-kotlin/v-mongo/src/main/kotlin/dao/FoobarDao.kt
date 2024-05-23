package dao

import com.mongodb.MongoException
import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Projections
import com.mongodb.client.model.Sorts
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import model.Foobar
import model.FoobarUnwind
import org.bson.conversions.Bson

class FoobarDao(database: MongoDatabase) {
  private val collection = database.getCollection<Foobar>("v_foo_bar")
  
  fun createBatch(foobar: List<Foobar>) = runBlocking {
    try {
      val result = collection.insertMany(foobar)
      println("Success! Inserted document id: " + result.insertedIds)
    } catch (e: MongoException) {
      System.err.println("Unable to insert due to an error: $e")
    }
  }
  
  fun count() = runBlocking { collection.countDocuments() }
  
  fun page() = runBlocking {
    val pipeline = mutableListOf<Bson>().apply {
      
      
      add(Aggregates.match(Filters.regex("foo.fooId", "999")))
      
      add(Aggregates.unwind("\$${"barList"}"))
      
      val project = Aggregates.project(
        Projections.fields(
          Projections.excludeId(),
          Projections.computed("foo.fooId", "\$foo.fooId"),
          Projections.computed("bar.barId", "\$barList.barId")
        )
      )
      add(project)
      
      Aggregates.sort(Sorts.ascending("foo.fooId"))
    }
    return@runBlocking collection.aggregate<FoobarUnwind>(pipeline, FoobarUnwind::class.java).toList()
  }
}