package de.htwg.se.mill.model.fieldComponent.fieldBaseImpl

trait Strategy {

  def createNewField(size: Int): Field = fill(new Field(size))

  def fill(field: Field): Field
}
