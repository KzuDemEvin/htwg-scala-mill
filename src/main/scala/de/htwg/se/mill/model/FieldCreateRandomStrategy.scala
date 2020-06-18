package de.htwg.se.mill.model

import scala.util.Random

class FieldCreateRandomStrategy extends FieldCreateStrategyTemplate {

  def fill(_field: Field): Field = {
    val num = 18
    var field = new Field(_field.size)
    for (i <- 0 until num) {
      field = placeRandomStone(field)
    }
    field
  }

  private def placeRandomStone (field: Field): Field = {
    var row = 0
    var col = 0
    do {
      row = Random.nextInt(field.size)
      col = Random.nextInt(field.size)
    }
    while (!field.available(row, col))
    val color = Random.nextInt(2)
    if (color == 0) {
      field.set(row, col, Cell(filled = true, Stone("w+")))
    } else {
      field.set(row, col, Cell(filled = true, Stone("b+")))
    }
  }
}