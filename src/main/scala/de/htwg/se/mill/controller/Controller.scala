package de.htwg.se.mill.controller

import de.htwg.se.mill.model.{Cell, Field, RandomStrategy, Stone}
import de.htwg.se.mill.util.{Observable, UndoManager}

import scala.swing.Publisher

class Controller(var field:Field) extends Publisher {

  private val undoManager = new UndoManager
  var gameState = GameState.handle(NewState())
  val firstStage = 18
  var roundCounter = 0


  def createEmptyField(size: Int): Unit = {
    field = new Field(size)
    gameState = GameState.handle(NewState())
    publish(new CellChanged)
  }

  def createRandomField(size: Int): Unit = {
    field = (new RandomStrategy).createNewField(size)
    gameState = GameState.handle(RandomState())
    publish(new CellChanged)
  }

  def fieldToString: String = field.toString

  def modeChoise(row: Int, col: Int): Unit = {
    if (roundCounter <= firstStage) {
      set(row, col)
    } else {

    }
  }

  def set(row: Int, col: Int): Unit = {
    roundCounter += 1
    if (roundCounter % 2 == 0) {
      undoManager.doStep(new SetCommand(row, col, Cell(true, Stone("w+")), this))
      gameState = GameState.handle(WhiteTurnState())
    } else {
      undoManager.doStep(new SetCommand(row, col, Cell(true, Stone("b+")), this))
      gameState = GameState.handle(BlackTurnState())
    }
    roundCounter = placedStones()
    publish(new CellChanged)
  }

  def modeTwo(row: Int, col: Int): Unit

  def undo: Unit = {
    roundCounter -= 1
    undoManager.undoStep
    gameState = GameState.handle(UndoState())
    publish(new CellChanged)
  }

  def redo: Unit = {
    roundCounter += 1
    undoManager.redoStep
    gameState = GameState.handle(RedoState())
    publish(new CellChanged)
  }

  def statusText:String = GameState.state

  def cell(row:Int, col:Int):Cell = field.cell(row, col)
  def isSet(row:Int, col:Int):Boolean = field.cell(row, col).isSet
  def available(row:Int, col:Int):Boolean = field.available(row, col)
  def possiblePosition(row:Int, col:Int):Boolean = field.possiblePosition(row, col)
  def placedStones(): Int = field.placedStones()
  def fieldsize:Int = field.size
}
