package de.htwg.se.mill.controller

import de.htwg.se.mill.model.{Cell, Field, RandomStrategy, Stone}
import de.htwg.se.mill.util.{Observable, UndoManager}

import scala.swing.Publisher

class Controller(var field:Field) extends Publisher {

  private val undoManager = new UndoManager
  var gameState = GameState.handle(InProgessState())
  var roundCounter = 0


  def createEmptyField(size: Int): Unit = {
    field = new Field(size)
    gameState = GameState.handle(NewState())
    publish(new CellChanged)
  }

  def createRandomField(size: Int): Unit = {
    field = (new RandomStrategy).createNewField(size)
    gameState = GameState.handle(NewState())
    publish(new CellChanged)
  }

  def fieldToString: String = field.toString

  def set(row: Int, col: Int, c: Cell): Unit = {
    roundCounter += 1
    if (roundCounter % 2 == 0) {
      undoManager.doStep(new SetCommand(row, col, Cell(true, Stone("w+")), this))
      gameState = GameState.handle(BlackTurnState())
    } else {
      undoManager.doStep(new SetCommand(row, col, Cell(true, Stone("b+")), this))
      gameState = GameState.handle(WhiteTurnState())
    }
    publish(new CellChanged)
  }

  def undo: Unit = {
    undoManager.undoStep
    gameState = GameState.handle(UndoState())
    publish(new CellChanged)
  }

  def redo: Unit = {
    undoManager.redoStep
    gameState = GameState.handle(RedoState())
    publish(new CellChanged)
  }

  def statusText:String = GameState.state

  def cell(row:Int, col:Int):Cell = field.cell(row, col)
  def isSet(row:Int, col:Int):Boolean = field.cell(row, col).isSet
  def available(row:Int, col:Int):Boolean = field.available(row, col)
  def fieldsize:Int = field.size

}
