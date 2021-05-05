package de.htwg.se.mill.model.dbComponent.fileIoDaoImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import de.htwg.se.mill.model.dbComponent.FileIODaoInterface
import org.mongodb.scala._
import org.mongodb.scala.model.Aggregates._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Sorts._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.model._
import org.mongodb.scala.result.{DeleteResult, InsertOneResult}

import scala.collection.JavaConverters._
import scala.concurrent.{Await, ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

class FileIODaoMongo extends FileIODaoInterface {
  val uri: String = "mongodb://" + sys.env.getOrElse("MONGODB_HOST", "localhost:27017")
  val client: MongoClient = MongoClient(uri)
  val database: MongoDatabase = client.getDatabase("mill")
  val fileIOCollection: MongoCollection[Document] = database.getCollection("FileIO")

  override def save(field: String, id: Option[Int] = None): Unit = {
    val doc: Document = Document("_id" -> 0, "field" -> field)
    fileIOCollection.insertOne(doc)

    val insertObservable: SingleObservable[InsertOneResult] = fileIOCollection.insertOne(doc)
    insertObservable.subscribe(new Observer[InsertOneResult] {
      override def onNext(result: InsertOneResult): Unit = println(s"inserted: $result")
      override def onError(e: Throwable): Unit = println(s"failed: $e")
      override def onComplete(): Unit = println("completed")
    })
  }

  override def load(fileIoID: Option[Int])(oncomplete: Option[String] => Unit): Unit = {
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    (fileIoID match {
      case Some(fileIoID) => fileIOCollection.find(equal("_id", fileIoID)).first().head()
      case None => fileIOCollection.find().first().head()
    }).onComplete({
      case Success(value) => oncomplete(Some(value.toJson()))
      case Failure(_) => oncomplete(None)
    })
  }

  override def loadAll()(oncomplete: Option[String] => Unit): Unit = {
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    fileIOCollection.find().head().onComplete({
      case Success(value) => oncomplete(Some(value.toJson()))
      case Failure(_) => oncomplete(None)
    })
  }

  override def delete(fileIoID: Int): Unit = {
    fileIOCollection.deleteOne(equal("_id", fileIoID)).subscribe(
      (dr: DeleteResult) => print(s"Deleted document with id ${fileIoID}\n"),
      (e: Throwable) => print(s"Error when deleting the document with id $fileIoID: $e\n")
    )
  }
}
