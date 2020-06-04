package de.htwg.se.mill.model

case class Cell(filled:Boolean, content: Stone) {
  def this(filled:Boolean = false) {
    this(filled, Stone(0, Color.noColor))
  }

  def isSet: Boolean = filled
  def getContent:Stone = content
}
