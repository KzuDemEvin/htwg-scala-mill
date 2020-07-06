package de.htwg.se.mill.controller.controllerComponent

import de.htwg.se.mill.model.fieldComponent.Cell
import scala.swing.Publisher

trait ControllerInterface extends Publisher {
  var tmpCell = (0,0)
  var setCounter = 0
  var moveCounter = 0
  var flyCounter = 0
  def createEmptyField(size: Int): Unit
  def createRandomField(size: Int): Unit
  def fieldToString: String

  def getRoundCounter:Int
  def selectDriveCommand():ModeState
  def set(row: Int, col: Int): Unit
  def moveStone(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int): Unit
  def fly(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int):Unit
  def removeStone(row:Int, col:Int):Boolean
  def undo: Unit
  def redo: Unit
  def checkMill(row:Int, col:Int):String
  def save: Unit
  def load: Unit
  def statusText:String
  def millText:String
  def cell(row:Int, col:Int):Cell
  def isSet(row:Int, col:Int):Boolean
  def available(row:Int, col:Int):Boolean
  def possiblePosition(row:Int, col:Int):Boolean
  def placedStones(): Int
  def placedWhiteStones():Int
  def placedBlackStones():Int
  def isNeigbour(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int):Boolean
  def fieldsize:Int
}

import scala.swing.event.Event

class CellChanged extends Event
class StoneRemoved extends Event
