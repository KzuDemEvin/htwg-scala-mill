package de.htwg.se.mill.model.fileIoComponent

trait FileIOInterface {
  def load(filename: Option[String]): FieldInterface
  def save(field: FieldInterface, filename: Option[String]): Unit
}
