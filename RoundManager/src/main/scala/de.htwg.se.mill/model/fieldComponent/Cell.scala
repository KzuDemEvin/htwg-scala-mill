package de.htwg.se.mill.model.fieldComponent

trait CellTrait {
  def isSet: Boolean

  def colorAsChar: Char
}

abstract case class Cell(filled: Boolean, content: Stone) extends CellTrait {
  override def isSet: Boolean = filled
}

private class WhiteStoneCell(filled: Boolean = true, content: Stone = Stone("w+")) extends Cell(filled, content) {
  override def toString: String = "White Stone"

  override def colorAsChar: Char = 'w'
}

private class BlackStoneCell(filled: Boolean = true, content: Stone = Stone("b+")) extends Cell(filled, content) {
  override def toString: String = "Black Stone"

  override def colorAsChar: Char = 'b'
}

private class EmptyCell(filled: Boolean = false, content: Stone = Stone("n")) extends Cell(filled, content) {
  override def toString: String = "No Stone"

  override def colorAsChar: Char = 'o'
}

object Cell {
  def apply(kind: String): Cell = kind match {
    case "cw" => new WhiteStoneCell()
    case "cb" => new BlackStoneCell()
    case _ => new EmptyCell()
  }
}
