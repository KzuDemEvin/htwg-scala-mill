package de.htwg.se.mill.model.fileIoComponent.fileIoJsonImpl

import de.htwg.se.mill.model.fileIoComponent.FileIOInterface

import scala.io.Source

class FileIO extends FileIOInterface {

  override def load(filename: Option[String] = Some("field.json")): String = {
    val sourceFile = Source.fromFile(filename match {
      case Some(fn) => fn
      case None => "field.json"
    })
    val source: String = sourceFile.getLines.mkString
    sourceFile.close()
    source
  }

  override def save(field: String, filename: Option[String] = Some("field.json")): Unit = {
    import java.io._
    val pw = new PrintWriter(new File(filename match {
      case Some(fn) => fn
      case None => "field.json"
    }))
    pw.write(field)
    pw.close()
  }
}
