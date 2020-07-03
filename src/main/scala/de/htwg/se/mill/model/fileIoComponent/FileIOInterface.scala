package de.htwg.se.mill.model.fileIoComponent

import de.htwg.se.mill.model.fieldComponent.FieldInterface

trait FileIOInterface {
  def load: FieldInterface
  def save(field: FieldInterface): Unit
}
