package de.htwg.se.mill.model

class FieldCreator() {
  def createField(size:Int): Field[Cell] = {
    val field = new Field(size, Cell(false))
    field
  }
}
