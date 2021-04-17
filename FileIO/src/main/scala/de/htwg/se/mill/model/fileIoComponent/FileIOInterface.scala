package de.htwg.se.mill.model.fileIoComponent

import de.htwg.se.mill.model.fieldComponent.FieldInterface

trait FileIOInterface {
  def load(filename: Option[String]): String

  // TODO
  def save(field: FieldInterface, filename: Option[String]): Unit
}
