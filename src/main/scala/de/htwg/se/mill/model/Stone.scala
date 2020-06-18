package de.htwg.se.mill.model

trait Stone {
  def isSet: Boolean
  def whichColor: Color.Value
}

private class WhiteStone(value:Int, color:Color.Value) extends Stone {
  override def isSet: Boolean = value != 0
  override def whichColor:Color.Value =  color
}

private class BlackStone(value:Int, color:Color.Value) extends Stone {
  override def isSet: Boolean = value != 0
  override def whichColor:Color.Value =  color
}

private class ColorLessStone(value:Int, color: Color.Value) extends Stone {
  override def isSet: Boolean = value != 0
  override def whichColor:Color.Value =  color
}

object Stone {
  def apply(kind: String):Stone = kind match{
    case "w+" => new WhiteStone(1, Color.white)
    case "w-" => new WhiteStone(0, Color.white)
    case "b+" => new BlackStone(1, Color.black)
    case "b-" => new BlackStone(0, Color.black)
    case "n" => new ColorLessStone(0, Color.noColor)
  }
}