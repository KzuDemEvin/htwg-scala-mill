package de.htwg.se.mill.model.fieldComponent.fieldBaseImpl

import de.htwg.se.mill.model.fieldComponent.CellInterface

case class Cell(filled:Boolean, content: Stone) extends CellInterface {
  def this() {
    this(false, Stone("n"))
  }

  def isSet: Boolean = filled
  def getContent:Stone = content
}
