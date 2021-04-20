package de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Injector}
import de.htwg.se.mill.MillModule
import de.htwg.se.mill.controller.controllerComponent._
import de.htwg.se.mill.model.fileIoComponent.FileIOInterface
import de.htwg.se.mill.model.playerComponent.Player
import de.htwg.se.mill.util.UndoManager
import net.codingwell.scalaguice.InjectorExtensions._

import scala.swing.Publisher

class Controller extends ControllerInterface with Publisher {
  private val undoManager = new UndoManager
  var gameState: String = GameState.handle(NewState())
  val injector: Injector = Guice.createInjector(new MillModule)
  val fileIo: FileIOInterface = injector.instance[FileIOInterface]

  def createPlayer(name: String, number: Int = 1): Player = {
    val player: Player = Player(name)
    player
  }

  def createEmptyField(size: Int): Unit = {
    // mgr.copy(winner = 0, roundCounter = 0, field = injector.instance[FieldInterface](Names.named("normal"))).modeChoice()
    gameState = GameState.handle(NewState())
    publish(new CellChanged)
  }

  def createRandomField(size: Int): Unit = {
    // mgr.copy(winner = 0, roundCounter = mgr.borderToMoveMode, field = injector.instance[FieldInterface](Names.named("random"))).modeChoice()
    gameState = GameState.handle(RandomState())
    publish(new CellChanged)
  }

  def handleClick(row: Int, col: Int): Unit = {
    // mgr.handleClick(row, col)
    gameState = GameState.handle(if (mgr.blackTurn()) BlackTurnState() else WhiteTurnState())
    publish(new CellChanged)
  }


  def undo: Unit = {
    undoManager.undoStep()
    gameState = GameState.handle(UndoState())
    publish(new CellChanged)
  }

  def redo: Unit = {
    undoManager.redoStep()
    gameState = GameState.handle(RedoState())
    publish(new CellChanged)
  }

  def checkMill(row: Int, col: Int): String = {
    mgr = this.mgr.copy(field = mgr.field.checkMill(row, col))
    mgr.field.millState
  }

  def save: Unit = {
    val field = mgr.field
      .setRoundCounter(mgr.roundCounter)
      .setPlayer1Mode(mgr.player1Mode)
      .setPlayer2Mode(mgr.player2Mode)
    mgr = mgr.copy(field = field)
    fileIo.save(field, None)
    gameState = GameState.handle(SaveState())
    publish(new CellChanged)
  }

  def load: Unit = {
    val field = fileIo.load(None)
    mgr = this.mgr.copy(field = field, roundCounter = field.savedRoundCounter)
      .setPlayerMode(field.player1Mode)
      .setPlayerMode(field.player2Mode, 2)
    gameState = GameState.handle(LoadState())
    publish(new CellChanged)
  }

  def cell(row: Int, col: Int): String = mgr.field.cell(row, col)
  def isSet(row: Int, col: Int): Boolean = mgr.field.cell(row, col).isSet
  def possiblePosition(row: Int, col: Int): Boolean = mgr.field.possiblePosition(row, col)
  def getMillState: String = mgr.field.millState
  def fieldsize: Int = mgr.field.size
  def fieldToHtml: String = mgr.field.toHtml
  def fieldToString: String = mgr.field.toString
  def getRoundManager: RoundManager = mgr
  def getRoundCounter: Int = mgr.roundCounter
}