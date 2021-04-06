package de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl

import com.google.inject.name.Names
import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.mill.MillModule
import de.htwg.se.mill.controller.controllerComponent._
import de.htwg.se.mill.model.fieldComponent.{Cell, Color, FieldInterface}
import de.htwg.se.mill.model.fileIoComponent.FileIOInterface
import de.htwg.se.mill.model.playerComponent.Player
import de.htwg.se.mill.util.{CommandTrait, UndoManager}
import net.codingwell.scalaguice.InjectorExtensions._

import scala.swing.Publisher

class Controller @Inject()(var field: FieldInterface) extends ControllerInterface with Publisher {
  private val undoManager = new UndoManager
  val mgr: RoundManager = RoundManager()
  var tmpCell: (Int, Int) = (0, 0)
  var setCounter = 0
  var moveCounter = 0
  var flyCounter = 0
  var gameState: String = GameState.handle(NewState())
  var millState: String = MillState.handle(NoMillState())
  var winnerText: String = "No Winner"
  val injector: Injector = Guice.createInjector(new MillModule)
  val fileIo: FileIOInterface = injector.instance[FileIOInterface]

  def createPlayer(name: String, number: Int = 1): Player = {
    val player: Player = Player(name)
    if (number == 1) {
      mgr.player1 = player
    } else {
      mgr.player2 = player
    }
    player
  }

  def createEmptyField(size: Int): Unit = {
    resetCounters()
    mgr.winner = 0
    mgr.roundCounter = 0
    field = injector.instance[FieldInterface](Names.named("normal"))
    mgr.modeChoice(field)
    gameState = GameState.handle(NewState())
    millState = MillState.handle(NoMillState())
    publish(new CellChanged)
  }

  def createRandomField(size: Int): Unit = {
    resetCounters()
    mgr.winner = 0
    mgr.roundCounter = mgr.borderToMoveMode
    field = injector.instance[FieldInterface](Names.named("random"))
    mgr.modeChoice(field)
    gameState = GameState.handle(RandomState())
    publish(new CellChanged)
  }

  def fieldToString: String = field.toString

  def getRoundCounter: Int = mgr.roundCounter

  def handleClick(row: Int, column: Int): Unit = {
    gameState = GameState.handle(if (mgr.blackTurn()) BlackTurnState() else WhiteTurnState())

    selectDriveCommand() match {
      case SetModeState() => setCounter = handleSet(row, column, setCounter)
      case MoveModeState() => moveCounter = handleMoveAndFly(row, column, moveCounter, MoveModeState())
      case FlyModeState() => flyCounter = handleMoveAndFly(row, column, flyCounter, FlyModeState())
    }
    publish(new CellChanged)
  }

  private def handleSet(row: Int, column: Int, counter: Int): Int = {
    var cnt = counter
    if (cnt >= 1) {
      if (removeStone(row, column)) {
        cnt = 0
        mgr.roundCounter += 1
      } else {
        cnt += 1
      }
    } else {
      handleSetHelper(row, column)
      checkMill(row, column) match {
        case "White Mill" | "Black Mill" => cnt += 1
        case "No Mill" => cnt = 0
          mgr.roundCounter += 1
      }
    }
    mgr.modeChoice(field)
    cnt
  }

  private def handleMoveAndFly(row: Int, column: Int, counter: Int, mode: ModeState): Int = {
    var cnt = counter + 1
    if (cnt == 2) {
      handleMoveAndFlyHelper(tmpCell._1, tmpCell._2, row, column,
        if (mode == MoveModeState()) {
          new MoveCommand(tmpCell._1, tmpCell._2, row, column, this)
        } else {
          new FlyCommand(tmpCell._1, tmpCell._2, row, column, this)
        })
      checkMill(row, column) match {
        case "White Mill" => cnt += 1
        case "Black Mill" => cnt += 1
        case "No Mill" => cnt = 0
          mgr.roundCounter += 1
      }
    } else if (cnt >= 4) {
      if (removeStone(row, column)) {
        cnt = 0
        mgr.roundCounter += 1
      } else {
        checkWinner(row, column)
      }
    } else {
      tmpCell = (row, column)
    }
    mgr.modeChoice(field)
    cnt
  }

  private def handleMoveAndFlyHelper(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int, command: CommandTrait): Unit = {
    if ((command.isInstanceOf[FlyCommand] && field.available(rowNew, colNew)) ||
      (command.isInstanceOf[MoveCommand] && field.available(rowNew, colNew) && isNeigbour(rowOld, colOld, rowNew, colNew))
    ) {
      val cellColor = cell(rowOld, colOld).content.color
      if (cellColor == Color.black || cellColor == Color.white) {
        undoManager.doStep(command)
      } else {
        mgr.roundCounter -= 1
      }
    } else {
      mgr.roundCounter -= 1
    }
    publish(new CellChanged)
  }

  def selectDriveCommand(): ModeState = mgr.selectDriveCommand()

  private def handleSetHelper(row: Int, col: Int): Unit = {
    if (field.available(row, col)) {
      undoManager.doStep(new SetCommand(row, col, if (mgr.blackTurn()) Cell("cb") else Cell("cw"), this))
      gameState = GameState.handle(if (mgr.blackTurn()) WhiteTurnState() else BlackTurnState())
    } else {
      mgr.roundCounter -= 1
    }
    publish(new CellChanged)
  }

  def undo: Unit = {
    undoManager.undoStep()
    if (mgr.roundCounter > 0) {
      mgr.roundCounter -= 1
      resetCounters()
    }
    gameState = GameState.handle(UndoState())
    publish(new CellChanged)
  }

  def redo: Unit = {
    undoManager.redoStep()
    if (mgr.roundCounter > 0) {
      mgr.roundCounter += 1
      resetCounters()
    }
    gameState = GameState.handle(RedoState())
    publish(new CellChanged)
  }

  def checkMill(row: Int, col: Int): String = {
    millState = field.checkMill(row, col) match {
      case 1 => MillState.handle(BlackMillState())
      case 2 => MillState.handle(WhiteMillState())
      case _ => MillState.handle(NoMillState())
    }
    millState
  }

  def removeStone(row: Int, col: Int): Boolean = {
    val r = stoneHasOtherColor(row, col, if (mgr.blackTurn()) Color.white else Color.black)
    mgr.modeChoice(field)
    publish(new CellChanged)
    r
  }

  def stoneHasOtherColor(row: Int, col: Int, color: Color.Value): Boolean = {
    var r = (field, false)
    if (cell(row, col).content.color == color) {
      r = field.removeStone(row, col)
      field = r._1
    }
    r._2
  }

  def checkWinner(row: Int, column: Int): Unit = {
    if (mgr.player1.mode == ModeState.handle(FlyModeState()) && mgr.player2.mode == ModeState.handle(FlyModeState())) {
      checkMill(row, column) match {
        case "White Mill" => mgr.winner = 2
          mgr.handleWinnerText(2)
        case "Black Mill" => mgr.winner = 1
          mgr.handleWinnerText(1)
        case "No Mill" => mgr.winner = 0
      }
    }
  }

  def save: Unit = {
    field = field.setRoundCounter(mgr.roundCounter)
      .setPlayer1Mode(mgr.player1.mode)
      .setPlayer1Name(mgr.player1.name)
      .setPlayer2Mode(mgr.player2.mode)
      .setPlayer2Name(mgr.player2.name)
    fileIo.save(field, None)
    gameState = GameState.handle(SaveState())
    publish(new CellChanged)
  }

  def load: Unit = {
    field = fileIo.load(None)
    mgr.roundCounter = field.savedRoundCounter
    mgr.player1 = mgr.player1.changeMode(field.player1Mode)
    mgr.player2 = mgr.player2.changeMode(field.player2Mode)
    gameState = GameState.handle(LoadState())
    publish(new CellChanged)
  }

  def resetCounters(): Unit = {
    setCounter = 0
    moveCounter = 0
    flyCounter = 0
  }

  def fieldToHtml: String = field.toHtml
  def cell(row:Int, col:Int):Cell = field.cell(row, col)
  def isSet(row:Int, col:Int):Boolean = field.cell(row, col).isSet
  def available(row:Int, col:Int):Boolean = field.available(row, col)
  def possiblePosition(row:Int, col:Int):Boolean = field.possiblePosition(row, col)
  def placedStones():Int = field.placedStones()
  def placedWhiteStones():Int = field.placedWhiteStones()
  def placedBlackStones():Int = field.placedBlackStones()
  def isNeigbour(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int):Boolean =
    field.isNeighbour(rowOld, colOld, rowNew, colNew)
  def fieldsize: Int = field.size
  def getRoundManager: RoundManager = mgr
}