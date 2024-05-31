package model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Foobar (
  @BsonId
  val id: ObjectId? = null,
  val foo: Foo,
  val barList: List<Bar>
)

data class Foo (
  val fooId: String
)

data class Bar (
  val barId: String
)

data class FoobarUnwind (
  val foo: Foo,
  val bar: Bar
)