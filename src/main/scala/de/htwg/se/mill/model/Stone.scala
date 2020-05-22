package de.htwg.se.mill.model

case class Stone(value: Boolean, color: String) {
  def isSet:Boolean = value
  def whichColor:String = color
}
