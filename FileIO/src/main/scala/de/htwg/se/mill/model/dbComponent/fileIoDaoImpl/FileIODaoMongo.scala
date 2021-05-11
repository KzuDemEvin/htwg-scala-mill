package de.htwg.se.mill.model.dbComponent.fileIoDaoImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import de.htwg.se.mill.model.dbComponent.FileIODaoInterface
import org.mongodb.scala._
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Projections.excludeId
import org.mongodb.scala.result.{DeleteResult, InsertOneResult}

import scala.concurrent.{ExecutionContextExecutor, Future}

case class FileIODaoMongo() extends FileIODaoInterface {
  val uri: String = "mongodb://root:MILL@" + sys.env.getOrElse("MONGODB_HOST", "localhost:27017")
  val client: MongoClient = MongoClient(uri)
  val database: MongoDatabase = client.getDatabase("mill")
  val fileIOCollection: MongoCollection[Document] = database.getCollection("FileIO")

  override def save(field: String): Unit = {
    printf(s"Saving file in MongoDB\n")
    val doc: Document = Document.apply(field)

    val insertObservable: SingleObservable[InsertOneResult] = fileIOCollection.insertOne(doc)
    insertObservable.subscribe(new Observer[InsertOneResult] {
      override def onNext(result: InsertOneResult): Unit = printf(s"inserted: $result\n")

      override def onError(e: Throwable): Unit = printf(s"failed: $e\n")

      override def onComplete(): Unit = printf(s"completed\n")
    })
  }

  override def load(fileIoID: String): Future[String] = {
    printf(s"Loading file $fileIoID in MongoDB\n")
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext
    fileIOCollection.find(equal("_id", new ObjectId(fileIoID))).projection(excludeId()).head().map(_.toJson)
  }

  override def loadAll(): Future[Seq[(Int, String)]] = {
    printf(s"Loading files in MongoDB\n")
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    val loadedFiles: Future[Seq[(Int, String)]] = fileIOCollection
      .find()
      .toFuture
      .map(list => list
        .map(_.toJson)
        .zipWithIndex
        .map((el: (String, Int)) => (el._2, el._1)))
    loadedFiles
  }

  override def delete(fileIoID: String): Unit = {
    printf(s"Deleting file $fileIoID in MongoDB\n")
    fileIOCollection.deleteOne(equal("_id", new ObjectId(fileIoID))).subscribe(
      (_: DeleteResult) => print(s"Deleted document with id $fileIoID\n"),
      (e: Throwable) => print(s"Error when deleting the document with id $fileIoID: $e\n")
    )
  }
}
