package de.htwg.se.mill.model

case class Stone(value: Int, color: String) {
  def isSet:Boolean = value != 0
  def whichColor:String = color
}
