package de.htwg.se.mill.controller.controllerComponent

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.RoundManager
import de.htwg.se.mill.model.playerComponent.Player
import de.htwg.se.mill.model.fieldComponent.{Cell, Color}

import scala.swing.Publisher

trait ControllerInterface extends Publisher {
  var gameState:String
  def createPlayer(name: String, number: Int): Player
  def createEmptyField(size: Int): Unit
  def createRandomField(size: Int): Unit
  def fieldToString: String
  def fieldToHtml: String

  def getRoundCounter:Int
  def handleClick(row: Int, column: Int): Unit
  def undo(): Unit
  def redo(): Unit
  def checkMill(row:Int, col:Int):String
  def save(): Unit
  def load(): Unit
  def cell(row:Int, col:Int):String
  def isSet(row:Int, col:Int):Boolean
  def possiblePosition(row:Int, col:Int):Boolean
  def fieldsize:Int
  def getRoundManager:RoundManager
  def getMillState: String
}

import scala.swing.event.Event

class CellChanged extends Event
class StoneRemoved extends Event
