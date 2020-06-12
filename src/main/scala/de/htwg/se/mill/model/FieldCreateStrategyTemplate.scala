package de.htwg.se.mill.model

trait FieldCreateStrategyTemplate {

  def createNewField(size:Int): Field = {
    var field  = new Field(size)
    field = prepare(field)
    field = fill(field)
    field
  }

  def prepare(field: Field): Field = {
    field
  }

  def fill(field: Field) : Field
}
