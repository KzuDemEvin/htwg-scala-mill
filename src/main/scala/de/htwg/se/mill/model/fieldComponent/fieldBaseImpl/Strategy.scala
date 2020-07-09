package de.htwg.se.mill.model.fieldComponent.fieldBaseImpl

trait Strategy {

  def createNewField(size:Int): Field = {
    var field  = new Field(size)
    field = fill(field)
    field
  }

  def fill(field: Field) : Field
}