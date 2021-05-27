package de.htwg.se.mill.model.fileIoComponent.fileToXmlImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import de.htwg.se.mill.model.fileIoComponent.FileIOInterface

import scala.concurrent.{ExecutionContextExecutor, Future}

class FileIO extends FileIOInterface {
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  override def load(filename: Option[String] = Some("field.xml")): Future[String] = {
    Future {
      val file = scala.xml.XML.loadFile(filename match {
        case Some(fn) => fn
        case None => "field.xml"
      })
      file.toString()
    }
  }

  override def save(field: String, filename: Option[String] = Some("field.xml")): Unit = {
    Future {
      import java.io._
      val pw = new PrintWriter(new File(filename match {
        case Some(fn) => fn
        case None => "field.xml"
      }))
      pw.write(field)
      pw.close()
    }
  }
}
