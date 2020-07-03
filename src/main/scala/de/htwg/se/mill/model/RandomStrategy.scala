package de.htwg.se.mill.model

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
    println(whiteCounter)
    println(blackCounter)
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
        field.set(row, col, Cell(filled = true, Stone("w+")))
      } else if (blackCounter < maxStones){
        blackCounter += 1
        field.set(row, col, Cell(filled = true, Stone("b+")))
      } else {field}
    } else {
      if (blackCounter < maxStones) {
        blackCounter += 1
        field.set(row, col, Cell(filled = true, Stone("b+")))
      } else if (whiteCounter < maxStones) {
        whiteCounter += 1
        field.set(row, col, Cell(filled = true, Stone("w+")))
      } else {field}
      }
    }
}