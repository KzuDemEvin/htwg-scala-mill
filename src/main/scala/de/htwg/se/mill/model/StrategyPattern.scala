package de.htwg.se.mill.model

trait StrategyPattern {

  def createNewField(size:Int): Field = {
    var field  = new Field(size)
    field = fill(field)
    field
  }

  def fill(field: Field) : Field
}
