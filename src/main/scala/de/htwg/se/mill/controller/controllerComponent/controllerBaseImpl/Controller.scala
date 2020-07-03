package de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl

import com.google.inject.name.Names
import com.google.inject.{Guice, Inject}
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.mill.MillModule
import de.htwg.se.mill.controller.controllerComponent._
import de.htwg.se.mill.model.fieldComponent.{Cell, FieldInterface}
import de.htwg.se.mill.model.playerComponent.Player
import de.htwg.se.mill.util.UndoManager

import scala.swing.Publisher

class Controller @Inject() (var field: FieldInterface) extends ControllerInterface with Publisher {

  private val undoManager = new UndoManager
  var gameState = GameState.handle(NewState())
  var millState = MillState.handle(NoMillState())
  var player1 = Player("Kevin")
  var player2 = Player("Manuel")
  val injector = Guice.createInjector(new MillModule)
  val borderToMoveMode = 18
  var roundCounter = 0


  def createEmptyField(size: Int): Unit = {
    roundCounter = 0

    field = injector.instance[FieldInterface](Names.named("normal"))
    gameState = GameState.handle(NewState())
    millState = MillState.handle(NoMillState())
    publish(new CellChanged)
  }

  def createRandomField(size: Int): Unit = {
    roundCounter = 18
    field = injector.instance[FieldInterface](Names.named("random"))
    gameState = GameState.handle(RandomState())
    modeChoice()
    publish(new CellChanged)
  }

  def fieldToString: String = field.toString

  def modeChoice(): Unit = {
    if (roundCounter < borderToMoveMode) {
      player1.mode = ModeState.handle(SetModeState())
      player2.mode = ModeState.handle(SetModeState())
    } else if (placedBlackStones() == 3 || placedWhiteStones() == 3) {
      if (placedWhiteStones() == 3) {
        player1.mode = ModeState.handle(FlyModeState())
      }
      if (placedBlackStones() == 3) {
        player2.mode = ModeState.handle(FlyModeState())
      }
    } else {
      player1.mode = ModeState.handle(MoveModeState())
      player2.mode = ModeState.handle(MoveModeState())
    }
  }

  def selectDriveCommand():ModeState = {
    //roundCounter += 1
    var cmd = ModeState.whichState(SetModeState().handle)
    if (roundCounter % 2 == 0) {
      cmd = ModeState.whichState(player1.mode)
    } else {
      cmd = ModeState.whichState(player2.mode)
    }
    cmd
  }

  def set(row: Int, col: Int): Unit = {
    roundCounter += 1
    //println("before" + roundCounter)
    if (roundCounter % 2 == 0) {
      undoManager.doStep(new SetCommand(row, col, Cell("cb"), this))
      gameState = GameState.handle(BlackTurnState())
    } else {
      undoManager.doStep(new SetCommand(row, col, Cell("cw"), this))
      gameState = GameState.handle(WhiteTurnState())
    }
    //roundCounter = placedStones()
    println("after" + roundCounter)
    checkMill(row, col)
    modeChoice()
    publish(new CellChanged)
  }

  def moveStone(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int): Unit = {
    roundCounter += 1
    if (roundCounter % 2 == 0) {
      undoManager.doStep(new MoveCommand(rowOld, colOld, rowNew, colNew, this))
      gameState = GameState.handle(BlackTurnState())
    } else {
      undoManager.doStep(new MoveCommand(rowOld, colOld, rowNew, colNew, this))
      gameState = GameState.handle(WhiteTurnState())
    }
    checkMill(rowNew, colNew)
    modeChoice()
    publish(new CellChanged)
  }

  def fly(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int):Unit = {
    roundCounter += 1
    if (roundCounter % 2 == 0) {
      undoManager.doStep(new FlyCommand(rowOld, colOld, rowNew, colNew, this))
      gameState = GameState.handle(BlackTurnState())
    } else {
      undoManager.doStep(new FlyCommand(rowOld, colOld, rowNew, colNew, this))
      gameState = GameState.handle(WhiteTurnState())
    }
    checkMill(rowNew, colNew)
    modeChoice()
    publish(new CellChanged)
  }

  def undo: Unit = {
    if (roundCounter > 0) {
      roundCounter -= 1
    }
    undoManager.undoStep
    gameState = GameState.handle(UndoState())
    publish(new CellChanged)
  }

  def redo: Unit = {
    if (roundCounter > 0) {
      roundCounter += 1
    }
    undoManager.redoStep
    gameState = GameState.handle(RedoState())
    publish(new CellChanged)
  }

  def checkMill(row:Int, col:Int):Unit = {
    val m = field.checkMill(row, col)
    m match {
      case 1 => millState = MillState.handle(BlackMillState())
      case 2 => millState = MillState.handle(WhiteMillState())
      case _ => millState = MillState.handle(NoMillState())
    }
  }

  def statusText:String = GameState.state
  def millText:String = MillState.state

  def cell(row:Int, col:Int):Cell = field.cell(row, col)
  def isSet(row:Int, col:Int):Boolean = field.cell(row, col).isSet
  def available(row:Int, col:Int):Boolean = field.available(row, col)
  def possiblePosition(row:Int, col:Int):Boolean = field.possiblePosition(row, col)
  def placedStones():Int = field.placedStones()
  def placedWhiteStones():Int = field.placedWhiteStones()
  def placedBlackStones():Int = field.placedBlackStones()
  def fieldsize:Int = field.size
}
