import com.mongodb.kotlin.client.coroutine.MongoClient
import dao.AlbumDao

fun main() {
  val uri = "mongodb://vitamin:vitamin@127.0.0.1:23017/"
  val mongoClient = MongoClient.create(uri)
  val database = mongoClient.getDatabase("vitamin")
  
  val albumDao = AlbumDao(database)
  albumDao.create()
  
//  albumDao.find()
  
  albumDao.page()
  
  mongoClient.close()
}