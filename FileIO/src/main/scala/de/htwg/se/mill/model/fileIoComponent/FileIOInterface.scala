package de.htwg.se.mill.model.fileIoComponent

import scala.concurrent.Future

trait FileIOInterface {
  def load(filename: Option[String]): Future[String]

  def save(field: String, filename: Option[String]): Unit
}
