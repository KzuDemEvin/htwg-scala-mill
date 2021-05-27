package de.htwg.se.mill.model.fileIoComponent.fileIoJsonImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import de.htwg.se.mill.model.fileIoComponent.FileIOInterface

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.io.Source

class FileIO extends FileIOInterface {
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  override def load(filename: Option[String] = Some("field.json")): Future[String] = {
    Future {
      val sourceFile = Source.fromFile(filename match {
        case Some(fn) => fn
        case None => "field.json"
      })
      val source: String = sourceFile.getLines.mkString
      sourceFile.close()
      source
    }
  }

  override def save(field: String, filename: Option[String] = Some("field.json")): Unit = {
    Future {
      import java.io._
      val pw = new PrintWriter(new File(filename match {
        case Some(fn) => fn
        case None => "field.json"
      }))
      pw.write(field)
      pw.close()
    }
  }
}
