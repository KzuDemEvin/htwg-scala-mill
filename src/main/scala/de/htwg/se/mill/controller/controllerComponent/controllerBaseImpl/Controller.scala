package de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl

import com.google.inject.name.Names
import com.google.inject.{Guice, Inject, Injector}
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.mill.MillModule
import de.htwg.se.mill.controller.controllerComponent._
import de.htwg.se.mill.model.fieldComponent.{Cell, Color, FieldInterface}
import de.htwg.se.mill.model.fileIoComponent.FileIOInterface
import de.htwg.se.mill.util.UndoManager

import scala.swing.Publisher

class Controller @Inject() (var field: FieldInterface) extends ControllerInterface with Publisher {
  private val undoManager = new UndoManager
  val mgr: RoundManager = RoundManager()
  var tmpCell: (Int, Int) = (0,0)
  var setCounter = 0
  var moveCounter = 0
  var flyCounter = 0
  var gameState: String = GameState.handle(NewState())
  var millState: String = MillState.handle(NoMillState())
  var winnerText: String = "No Winner"
  val injector: Injector = Guice.createInjector(new MillModule)
  val fileIo: FileIOInterface = injector.instance[FileIOInterface]

  def createEmptyField(size: Int): Unit = {
    setCounter = 0
    moveCounter = 0
    flyCounter = 0
    mgr.roundCounter = 0
    mgr.winner = 0
    field = injector.instance[FieldInterface](Names.named("normal"))
    mgr.modeChoice(field)
    gameState = GameState.handle(NewState())
    millState = MillState.handle(NoMillState())
    publish(new CellChanged)
  }

  def createRandomField(size: Int): Unit = {
    setCounter = 0
    moveCounter = 0
    flyCounter = 0
    mgr.winner = 0
    mgr.roundCounter = mgr.borderToMoveMode
    field = injector.instance[FieldInterface](Names.named("random"))
    mgr.modeChoice(field)
    gameState = GameState.handle(RandomState())
    publish(new CellChanged)
  }

  def fieldToString: String = field.toString

  def getRoundCounter: Int = {
    mgr.roundCounter
  }


  def handleClick(row: Int, column: Int): Unit = {
    if (mgr.blackTurn()) {
      gameState = GameState.handle(BlackTurnState())
    } else {
      gameState = GameState.handle(WhiteTurnState())
    }

    val whichCmd = selectDriveCommand()
    whichCmd match {
      case SetModeState() => setCounter = handleSet(row, column, setCounter)
      case MoveModeState() => moveCounter = handleMoveAndFly(row, column, moveCounter, MoveModeState())
      case FlyModeState() => flyCounter = handleMoveAndFly(row, column, flyCounter, FlyModeState())
    }
  }

  def handleSet(row:Int, column:Int, counter:Int):Int = {
    var cnt = counter
    if (cnt >= 1) {
      if (removeStone(row, column)) {
        cnt = 0
        mgr.roundCounter += 1
      } else {
        cnt += 1
      }
    } else {
      set(row, column)
      val m = checkMill(row, column)
      m match {
        case "White Mill" => cnt += 1
        case "Black Mill" => cnt += 1
        case "No Mill" => cnt = 0
          mgr.roundCounter += 1
      }
    }
    mgr.modeChoice(field)
    cnt
  }

  def handleMoveAndFly(row:Int, column:Int, counter:Int, mode:ModeState):Int = {
    var cnt = counter
    cnt += 1
    if (cnt == 2) {
      if (mode == MoveModeState()) {
        moveStone(tmpCell._1, tmpCell._2, row, column)
        println("movecounter: " + cnt)
      } else {
        fly(tmpCell._1, tmpCell._2, row, column)
        println("flycounter: " + cnt)
      }
      val m = checkMill(row, column)
      m match {
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

  def selectDriveCommand():ModeState = {
    mgr.selectDriveCommand()
  }

  def set(row: Int, col: Int): Unit = {
    if (field.available(row, col)) {
      if (mgr.blackTurn()) {
        undoManager.doStep(new SetCommand(row, col, Cell("cb"), this))
        gameState = GameState.handle(WhiteTurnState())
      } else {
        undoManager.doStep(new SetCommand(row, col, Cell("cw"), this))
        gameState = GameState.handle(BlackTurnState())
      }
    } else {
      mgr.roundCounter -= 1
    }
    publish(new CellChanged)
  }

  def moveStone(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int): Unit = {
    if (field.available(rowNew, colNew) && isNeigbour(rowOld, colOld, rowNew, colNew)) {
      if (mgr.blackTurn()) {
        if (cell(rowOld, colOld).getContent.whichColor == Color.black) {
          undoManager.doStep(new MoveCommand(rowOld, colOld, rowNew, colNew, this))
        } else {
          mgr.roundCounter -= 1
        }
      } else {
        if (cell(rowOld, colOld).getContent.whichColor == Color.white) {
          undoManager.doStep(new MoveCommand(rowOld, colOld, rowNew, colNew, this))
        } else {
          mgr.roundCounter -= 1
        }
      }
    } else {
      mgr.roundCounter -= 1
    }
    publish(new CellChanged)
  }

  def fly(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int):Unit = {
    if (field.available(rowNew, colNew)) {
      if (mgr.blackTurn()) {
        if (cell(rowOld, colOld).getContent.whichColor == Color.black) {
          undoManager.doStep(new FlyCommand(rowOld, colOld, rowNew, colNew, this))
        } else {
          mgr.roundCounter -= 1
        }
      } else {
        if (cell(rowOld, colOld).getContent.whichColor == Color.white) {
          undoManager.doStep(new FlyCommand(rowOld, colOld, rowNew, colNew, this))
        } else {
          mgr.roundCounter -= 1
        }
      }
    }

    publish(new CellChanged)
  }

  def undo: Unit = {
    undoManager.undoStep()
    if (mgr.roundCounter > 0) {
      mgr.roundCounter -= 1
      flyCounter = 0
      moveCounter = 0
      setCounter = 0
    }
    gameState = GameState.handle(UndoState())
    publish(new CellChanged)
  }

  def redo: Unit = {
    undoManager.redoStep()
    if (mgr.roundCounter > 0) {
      mgr.roundCounter += 1
      flyCounter = 0
      moveCounter = 0
      setCounter = 0
    }
    gameState = GameState.handle(RedoState())
    publish(new CellChanged)
  }

  def checkMill(row:Int, col:Int):String = {
    val m = field.checkMill(row, col)
    m match {
      case 1 => millState = MillState.handle(BlackMillState())
      case 2 => millState = MillState.handle(WhiteMillState())
      case _ => millState = MillState.handle(NoMillState())
    }
    millState
  }

  def removeStone(row: Int, col: Int): Boolean = {
    var r = false
    if (mgr.blackTurn()) {
      r = stoneHasOtherColor(row, col, Color.white)
      mgr.modeChoice(field)
      publish(new CellChanged)
    } else {
      r = stoneHasOtherColor(row, col, Color.black)
      mgr.modeChoice(field)
      publish(new CellChanged)
    }
    r
  }

  def stoneHasOtherColor(row:Int, col:Int, color: Color.Value):Boolean = {
    var r = (field, false)
    if (cell(row, col).getContent.whichColor == color) {
      r = field.removeStone(row, col)
      field = r._1
    }
    r._2
  }

  def checkWinner(row:Int, column:Int): Unit = {
    if (mgr.player1.mode == ModeState.handle(FlyModeState()) && mgr.player2.mode == ModeState.handle(FlyModeState())) {
      val m = checkMill(row, column)
      m match {
        case "White Mill" => mgr.winner = 2
          mgr.handleWinnerText(2)
        case "Black Mill" => mgr.winner = 1
          mgr.handleWinnerText(1)
        case "No Mill" => mgr.winner = 0
      }
    }
  }

  def save: Unit = {
    field.setRoundCounter(mgr.roundCounter)
    field.setPlayer1Mode(mgr.player1.mode)
    field.setPlayer1Name(mgr.player1.name)
    field.setPlayer2Mode(mgr.player2.mode)
    field.setPlayer2Name(mgr.player2.name)
    fileIo.save(field)
    gameState = GameState.handle(SaveState())
    publish(new CellChanged)
  }

  def load: Unit = {
    field = fileIo.load
    mgr.roundCounter = field.savedRoundCounter
    mgr.player1 = mgr.player1.changeMode(field.player1Mode)
    mgr.player2 = mgr.player2.changeMode(field.player2Mode)
    gameState = GameState.handle(LoadState())
    publish(new CellChanged)
  }

  def cell(row:Int, col:Int):Cell = field.cell(row, col)
  def isSet(row:Int, col:Int):Boolean = field.cell(row, col).isSet
  def available(row:Int, col:Int):Boolean = field.available(row, col)
  def possiblePosition(row:Int, col:Int):Boolean = field.possiblePosition(row, col)
  def placedStones():Int = field.placedStones()
  def placedWhiteStones():Int = field.placedWhiteStones()
  def placedBlackStones():Int = field.placedBlackStones()
  def isNeigbour(rowOld: Int, colOld: Int, rowNew: Int, colNew: Int):Boolean =
    field.isNeigbour(rowOld, colOld, rowNew, colNew)
  def fieldsize:Int = field.size
  def getRoundManager:RoundManager = mgr
}