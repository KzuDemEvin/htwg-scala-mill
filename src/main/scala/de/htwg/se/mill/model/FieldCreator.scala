package de.htwg.se.mill.model

import scala.util.Random

class FieldCreator(size:Int) {


  def fillRandomly(amountStones: Int): Field = {
    var modField = new Field(size)
    for {n <- 1 to amountStones} {
      modField = setStoneAnywhere(modField)
    }
    modField
  }

  def setStoneAnywhere(field: Field): Field = {
    val row = Random.nextInt(field.size)
    val col = Random.nextInt(field.size)
    val color = Random.nextInt(Color.maxId)
    val colorset = Color.values.toIndexedSeq
    if (field.available(row, col)) {
      field.set(row, col, Cell(filled = true, Stone(1, colorset.apply(color))))
    } else {
      field
    }
  }
}
