package de.htwg.se.mill.model.fileIoComponent

trait FileIOInterface {
  def load(filename: Option[String]): String

  def save(field: String, filename: Option[String]): Unit
}
