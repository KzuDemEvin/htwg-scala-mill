package de.htwg.se.mill.model.fieldComponent

trait CellTrait {
  def isSet: Boolean
  def getContent: Stone
}

abstract class Cell(filled: Boolean, content: Stone) extends CellTrait {
  override def isSet: Boolean = filled
  override def getContent: Stone = content
}

private class WhiteStoneCell(filled: Boolean, content: Stone) extends Cell(filled, content) {
  override def toString: String = "White Stone"
}

private class BlackStoneCell(filled: Boolean, content: Stone) extends Cell(filled, content)  {
  override def toString: String = "Black Stone"
}

private class EmptyCell(filled: Boolean, content: Stone) extends Cell(filled, content)  {
  override def toString: String = "No Stone"
}

object Cell {
  def apply(kind: String): Cell = kind match {
    case "cw" => new WhiteStoneCell(true, Stone("w+"))
    case "cb" => new BlackStoneCell(true, Stone("b+"))
    case "ce" => new EmptyCell(false, Stone("n"))
  }
}
