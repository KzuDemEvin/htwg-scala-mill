package de.htwg.se.mill.controller.controllerComponent

import de.htwg.se.mill.model.fieldComponent.{Cell, Color}

import scala.swing.Publisher

trait ControllerInterface extends Publisher {
  var tmpCell:(Int,Int)
  var setCounter:Int
  var moveCounter:Int
  var flyCounter:Int
  var winnerText:String
  def createEmptyField(size: Int): Unit
  def createRandomField(size: Int): Unit
  def fieldToString: String

  def getRoundCounter:Int
  def selectDriveCommand():ModeState
  def handleClick(row: Int, column: Int): Unit
  def handleSet(row:Int, column:Int, counter:Int):Int
  def handleMoveAndFly(row:Int, column:Int, counter:Int, move:ModeState):Int
  def set(row: Int, col: Int): Unit
  def moveStone(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int): Unit
  def fly(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int):Unit
  def removeStone(row:Int, col:Int):Boolean
  def stoneHasOtherColor(row:Int, col:Int, color: Color.Value):Boolean
  def undo(): Unit
  def redo(): Unit
  def checkMill(row:Int, col:Int):String
  def checkWinner(): Int
  def save(): Unit
  def load(): Unit
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
