package de.htwg.se.mill.model.fieldComponent

trait Cell {
  def isSet: Boolean
  def getContent: Stone
}

private class WhiteStoneCell(filled: Boolean, content: Stone) extends Cell {
  override def isSet: Boolean = filled
  override def getContent: Stone = content
  override def toString: String = "White Stone"
}

private class BlackStoneCell(filled: Boolean, content: Stone) extends Cell {
  override def isSet: Boolean = filled
  override def getContent: Stone = content
  override def toString: String = "Black Stone"
}

private class EmptyCell(filled: Boolean, content: Stone) extends Cell {
  override def isSet: Boolean = filled
  override def getContent: Stone = content
  override def toString: String = "No Stone"
}

object Cell {
  def apply(kind: String): Cell = kind match {
    case "cw" => new WhiteStoneCell(true, Stone("w+"))
    case "cb" => new BlackStoneCell(true, Stone("b+"))
    case "ce" => new EmptyCell(false, Stone("n"))
  }
}
