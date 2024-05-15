package dao

import com.mongodb.MongoException
import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Projections
import com.mongodb.client.model.UnwindOptions
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import model.Album
import model.Music
import org.bson.types.ObjectId

class AlbumDao(database: MongoDatabase) {
  private val collection = database.getCollection<Album>("album")
  
  private val album1 = Album(
    ObjectId(),
    "空气蛹",
    listOf(
      Music("在银河中孤独摇摆"),
      Music("使一颗心免于哀伤"),
      Music("希望有羽毛和翅膀"),
      Music("若我不曾见过太阳"),
    )
  )
  
  private val album2 = Album(
    ObjectId(),
    "慈悲灯塔",
    listOf(
      Music("Broken sun"),
      Music("Ratio ultima"),
      Music("Here come the exiles"),
    )
  )
  
  fun create() = runBlocking {
    try {
      val result = collection.insertOne(album1)
      println("Success! Inserted document id: " + result.insertedId)
    } catch (e: MongoException) {
      System.err.println("Unable to insert due to an error: $e")
    }
  }
  
  fun find() = runBlocking {
    val doc = collection.find(Filters.eq("title", "慈悲灯塔")).firstOrNull()
    if (doc != null) {
      println(doc)
    } else {
      println("No matching documents found.")
    }
  }
  
  fun page() = runBlocking {
    val pipeline = listOf(
      Aggregates.unwind(
        "\$${"musicList"}",
      ),
      Aggregates.project(
        Projections.fields(
          Projections.excludeId(),
          Projections.computed("title", "\$musicList.title")
        )
      )
    )
    println(pipeline)
    val results = collection.aggregate(pipeline, Music::class.java).toList()
    for (result in results) {
      println(result)
    }
  }
}