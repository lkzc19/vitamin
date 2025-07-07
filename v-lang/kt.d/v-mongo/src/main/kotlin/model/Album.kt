package model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Album (
  @BsonId
  val id: ObjectId,
  val title: String,
  val musicList: List<Music>
)