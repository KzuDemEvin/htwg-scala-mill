package de.htwg.se.mill.model

import scala.util.Random

class FieldCreator() {

  def createField(size:Int): Field = {
    if (size % 2 == 1) {
      val field = new Field(size)
      field
    } else {
      val field = new Field(size + 1)
      field
    }
  }
//
//  def fillRandomly(size:Int, amountStones: Int): Field = {
//    var modField = createField(size)
//    for {n <- 0 until amountStones} {
//      modField = setStoneAnywhere(modField)
//    }
//    modField
//  }
//
//  def setStoneAnywhere(field: Field): Field = {
//    var row = 0
//    var col = 0
//    do {
//      row = Random.nextInt(field.size)
//      col = Random.nextInt(field.size)
//    }
//    while (!field.available(row, col))
//    val color = Random.nextInt(2)
//    val colorset = Color.values.toIndexedSeq
//    field.set(row, col, Cell(filled = true, Stone(1, colorset.apply(color))))
//  }
}
