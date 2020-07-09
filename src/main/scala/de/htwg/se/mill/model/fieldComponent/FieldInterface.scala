package de.htwg.se.mill.model.fieldComponent

import de.htwg.se.mill.controller.controllerComponent.ModeState

trait FieldInterface {
  def size: Int

  def cell(row: Int, col: Int): Cell
  def possiblePosition(row: Int, col: Int): Boolean
  def available(row: Int, col: Int): Boolean
  def set(row:Int, col:Int, c:Cell) : FieldInterface
  def replace(row:Int, col:Int, c:Cell) : FieldInterface
  def moveStone(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int): FieldInterface
  def isNeigbour(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int):Boolean
  def fly(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int):FieldInterface
  def removeStone(row:Int, col:Int):(FieldInterface, Boolean)
  def placedStones(): Int
  def placedWhiteStones():Int
  def placedBlackStones():Int
  def checkMill(row: Int, col: Int): Int

  def createNewField:FieldInterface

  def setRoundCounter(counter: Int): Unit
  def getRoundCounter(): Int

  def setPlayer1Mode(mode: String): Unit
  def getPlayer1Mode(): String

  def setPlayer2Mode(mode: String): Unit
  def getPlayer2Mode(): String
}