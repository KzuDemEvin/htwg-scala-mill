package de.htwg.se.mill.controller.controllerComponent

import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Cell

import scala.swing.Publisher

trait ControllerInterface extends Publisher {

  def createEmptyField(size: Int): Unit
  def createRandomField(size: Int): Unit
  def fieldToString: String

  def set(row: Int, col: Int): Unit
  def moveStone(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int): Unit
  def fly(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int):Unit
  def undo: Unit
  def redo: Unit
  def checkMill(row:Int, col:Int):Unit
  def statusText:String
  def millText:String
  def cell(row:Int, col:Int):Cell
  def isSet(row:Int, col:Int):Boolean
  def available(row:Int, col:Int):Boolean
  def possiblePosition(row:Int, col:Int):Boolean
  def placedStones(): Int
  def placedWhiteStones():Int
  def placedBlackStones():Int
  def modeChoice():Unit
  def selectDriveCommand():ModeState
  def fieldsize:Int
}

import scala.swing.event.Event

class CellChanged extends Event
class StoneRemoved extends Event
