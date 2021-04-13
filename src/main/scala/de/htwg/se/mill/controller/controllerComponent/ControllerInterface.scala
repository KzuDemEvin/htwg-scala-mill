package de.htwg.se.mill.controller.controllerComponent

import de.htwg.se.mill.controller.controllerBaseImpl.RoundManager
import de.htwg.se.mill.model.playerComponent.Player
import de.htwg.se.mill.model.fieldComponent.{Cell, Color}

import scala.swing.Publisher

trait ControllerInterface extends Publisher {
  var tmpCell:(Int,Int)
  var setCounter:Int
  var moveCounter:Int
  var flyCounter:Int
  var gameState:String
  var millState:String
  def createPlayer(name: String, number: Int): Player
  def createEmptyField(size: Int): Unit
  def createRandomField(size: Int): Unit
  def fieldToString: String
  def fieldToHtml: String

  def getRoundCounter:Int
  def selectDriveCommand():ModeState
  def handleClick(row: Int, column: Int): Unit
  def undo(): Unit
  def redo(): Unit
  def checkMill(row:Int, col:Int):String
  def checkWinner(row:Int, column:Int): Unit
  def save(): Unit
  def load(): Unit
  def cell(row:Int, col:Int):Cell
  def isSet(row:Int, col:Int):Boolean
  def available(row:Int, col:Int):Boolean
  def possiblePosition(row:Int, col:Int):Boolean
  def placedStones(): Int
  def placedWhiteStones():Int
  def placedBlackStones():Int
  def isNeigbour(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int):Boolean
  def fieldsize:Int
  def getRoundManager:RoundManager
}

import scala.swing.event.Event

class CellChanged extends Event
class StoneRemoved extends Event
