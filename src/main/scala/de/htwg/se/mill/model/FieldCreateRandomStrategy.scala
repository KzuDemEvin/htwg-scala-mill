package de.htwg.se.mill.model

class FieldCreateRandomStrategy extends FieldCreateStrategyTemplate {

  def fill(_field: Field): Field = {
    val num = 24
    var field = new Field(_field.size)
    for (i <- 0 until num) {
      field = placeRandomStone(field)
    }
    field
  }

  private def placeRandomStone (field: Field): Field = {
    val newField = new FieldCreator().setStoneAnywhere(field)
    newField
  }
}