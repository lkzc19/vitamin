package dao

import com.mongodb.MongoException
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import model.Album
import model.Music
import org.bson.types.ObjectId

class AlbumDao(database: MongoDatabase) {
  // Get a collection of documents of type Movie
  private val collection = database.getCollection<Album>("movie")
  
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
      val result = collection.insertOne(album2)
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
}