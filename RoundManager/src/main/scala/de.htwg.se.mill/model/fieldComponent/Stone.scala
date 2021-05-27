package de.htwg.se.mill.model.fieldComponent

trait StoneTrait {
  def isSet: Boolean
}

abstract case class Stone(value: Int, color: Color.Value) extends StoneTrait {
  override def isSet: Boolean = value != 0
}

private class WhiteStone(value: Int, color: Color.Value = Color.white) extends Stone(value, color)

private class BlackStone(value: Int, color: Color.Value = Color.black) extends Stone(value, color)

private class ColorLessStone(value: Int = 0, color: Color.Value = Color.noColor) extends Stone(value, color)

object Stone {
  def apply(kind: String): Stone = kind match {
    case "w+" => new WhiteStone(1)
    case "w-" => new WhiteStone(0)
    case "b+" => new BlackStone(1)
    case "b-" => new BlackStone(0)
    case _ => new ColorLessStone()
  }
}