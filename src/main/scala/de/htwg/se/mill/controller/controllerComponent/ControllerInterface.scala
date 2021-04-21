package de.htwg.se.mill.controller.controllerComponent

import scala.swing.Publisher
import scala.swing.event.Event

trait ControllerInterface extends Publisher {
  var gameState:String
  def createPlayer(name: String, number: Int): String
  def createEmptyField(size: Int): Unit
  def createRandomField(size: Int): Unit
  def fieldToString: String
  def fieldToHtml: String

  def getRoundCounter:Int
  def handleClick(row: Int, column: Int): Unit
  def undo(): Unit
  def redo(): Unit
  def save(): Unit
  def load(): Unit
  def color(row: Int, col: Int): String
  def isSet(row:Int, col:Int):Boolean
  def possiblePosition(row:Int, col:Int):Boolean
  def fieldsize:Int
  def getWinner: Int
  def getWinnerText: String
  def getMillState: String
}

class DataArrived extends Event
class CellChanged extends Event
class StoneRemoved extends Event
