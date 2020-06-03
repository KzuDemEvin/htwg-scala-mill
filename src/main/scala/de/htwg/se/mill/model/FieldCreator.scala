package de.htwg.se.mill.model

class FieldCreator() {
  def createField(size:Int): Field[Cell] = {
    if (size % 2 == 1) {
      val field = new Field(size, Cell(false))
      field
    }
    else {
      throw new RuntimeException("Fieldsize must be odd")
    }
  }
}
