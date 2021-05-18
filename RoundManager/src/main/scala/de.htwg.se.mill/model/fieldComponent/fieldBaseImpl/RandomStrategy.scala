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
    for (counter <- 0 until num) {
      field = placeRandomStone(field, counter)
    }
    field
  }

  private def placeRandomStone(field: Field, counter: Int): Field = {
    val notFilledFields = findNotFilledFields(field)
    val (row, col) = notFilledFields(Random.nextInt(notFilledFields.size))

    if (counter % 2 == 0 && whiteCounter < maxStones) {
      field.set(row, col, Cell("cw"))._1
    } else if (blackCounter < maxStones) {
      field.set(row, col, Cell("cb"))._1
    } else {
      field
    }
  }

  private def findNotFilledFields(field: Field): Vector[(Int, Int)] = {
    (for {
      row <- 0 until field.size
      col <- 0 until field.size
      if field.available(row, col)
    } yield (row, col)).toVector
  }
}