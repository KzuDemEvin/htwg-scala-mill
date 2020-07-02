package de.htwg.se.mill.model.fieldComponent

import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.{Cell, Field, Stone}

trait FieldInterface {
  def size: Int

  def cell(row: Int, col: Int): Cell
  def possiblePosition(row: Int, col: Int): Boolean
  def available(row: Int, col: Int): Boolean
  def set(row:Int, col:Int, c:Cell) : Field
  def replace(row:Int, col:Int, c:Cell) : Field
  def moveStone(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int): Field
  def fly(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int):Field
  def placedStones(): Int
  def placedWhiteStones():Int
  def placedBlackStones():Int
  def checkMill(row: Int, col: Int): Int

  def createNewGrid:FieldInterface
}

trait CellInterface {
  def filled: Boolean
  def content: Stone

  def isSet: Boolean
  def getContent: Stone
}
