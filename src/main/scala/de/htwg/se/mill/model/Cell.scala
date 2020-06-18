package de.htwg.se.mill.model

case class Cell(filled:Boolean, content: Stone) {
  def this() {
    this(false, Stone("n"))
  }

  def isSet: Boolean = filled
  def getContent:Stone = content
}
