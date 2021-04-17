package de.htwg.se.mill.model.fileIoComponent.fileToXmlImpl

import de.htwg.se.mill.model.fileIoComponent.FileIOInterface

class FileIO extends FileIOInterface {

  override def load(filename: Option[String] = Some("field.xml")): String = {
    val file = scala.xml.XML.loadFile(filename match {
      case Some(fn) => fn
      case None => "field.xml"
    })
    file.toString()
  }

  override def save(field: String, filename: Option[String] = Some("field.xml")): Unit = {
    import java.io._
    val pw = new PrintWriter(new File(filename match {
      case Some(fn) => fn
      case None => "field.xml"
    }))
    pw.write(field)
    pw.close()
  }
}
