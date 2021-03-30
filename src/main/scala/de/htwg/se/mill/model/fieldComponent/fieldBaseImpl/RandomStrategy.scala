package de.htwg.se.mill.model.fieldComponent.fieldBaseImpl

import de.htwg.se.mill.model.fieldComponent.Cell

import scala.util.Random

class RandomStrategy extends Strategy {

  var whiteCounter = 0
  var blackCounter = 0
  val maxStones = 9

  def fill(_field: Field): Field = {
    val num = 18
    var field = new Field(_field.size)
    for (_ <- 0 until num) {
      field = placeRandomStone(field)
    }
    field
  }

  private def placeRandomStone(field: Field, counter: Int): Field = {
    val notFilledFields = findNotFilledFields(field)
    val (row, col) = notFilledFields(Random.nextInt(notFilledFields.size))

    if (counter % 2 == 0 && whiteCounter < maxStones) {
      field.set(row, col, Cell("cw"))
    } else if (blackCounter < maxStones) {
      field.set(row, col, Cell("cb"))
    } else {
      field
    }
  }
}