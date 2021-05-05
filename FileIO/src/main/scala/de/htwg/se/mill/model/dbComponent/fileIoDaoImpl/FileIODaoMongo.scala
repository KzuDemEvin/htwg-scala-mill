package de.htwg.se.mill.model.dbComponent.fileIoDaoImpl

import de.htwg.se.mill.model.dbComponent.FileIODaoInterface
import org.mongodb.scala._
import org.mongodb.scala.model.Aggregates._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Sorts._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.model._

import scala.collection.JavaConverters._

class FileIODaoMongo extends FileIODaoInterface {
  val uri: String = "mongodb://" + sys.env.getOrElse("MONGODB_HOST", "localhost:27017")
  val client: MongoClient = MongoClient(uri)
  val database: MongoDatabase = client.getDatabase("mill")
  val fileIOCollection: MongoCollection[Document] = database.getCollection("FileIO")

  override def save(field: String): Unit = {
    val doc = Document("_id" -> 0, "field" -> field)
    fileIOCollection.insertOne(doc)
  }

  override def load(fieldId: Int): String = {
    fileIOCollection.find(equal("_id", fieldId)).first().printHeadResult()
  }

  override def load(): Map[Int, String] = {
    ""
  }
}
