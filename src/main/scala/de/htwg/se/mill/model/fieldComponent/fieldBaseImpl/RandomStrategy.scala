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
    var color = Random.nextInt(2)
    if (color == 0) {
      if (whiteCounter < maxStones) {
        whiteCounter += 1
        field.set(row, col, Cell("cw"))
      } else if (blackCounter < maxStones){
        blackCounter += 1
        field.set(row, col, Cell("cb"))
      } else {field}
    } else {
      if (blackCounter < maxStones) {
        blackCounter += 1
        field.set(row, col, Cell("cb"))
      } else if (whiteCounter < maxStones) {
        whiteCounter += 1
        field.set(row, col, Cell("cw"))
      } else {field}
      }
    }
}