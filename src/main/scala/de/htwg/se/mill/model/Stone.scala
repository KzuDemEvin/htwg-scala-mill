package de.htwg.se.mill.model

case class Stone(value: Int, color: Color.Value) {
  def isSet:Boolean = value != 0
  def whichColor:Color.Value = color

}
