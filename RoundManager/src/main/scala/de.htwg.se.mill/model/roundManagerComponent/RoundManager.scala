package de.htwg.se.mill.model.roundManagerComponent

import de.htwg.se.mill.model._
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Field
import de.htwg.se.mill.model.fieldComponent.{Color, _}
import play.api.libs.json.Json

case class RoundManager(field: FieldInterface,
                        player1Mode: String = ModeState.handle(SetModeState()),
                        player2Mode: String = ModeState.handle(SetModeState()),
                        tmpCell: (Int, Int) = (-1, -1),
                        roundCounter: Int = 0,
                        setCounter: Int = 0,
                        borderToMoveMode: Int = 18,
                        update: (Int, Int) = (-1, -1),
                        winner: Int = 0,
                        @deprecated winnerText: String = "No Winner") {

  def this() {
    this(field = new Field(size = 7))
  }

  def blackTurn(): Boolean = roundCounter % 2 == 1

  def whiteTurn(): Boolean = roundCounter % 2 == 0

  def handleClick(row: Int, col: Int): RoundManager = {
    (ModeState.whichState(if (blackTurn()) player2Mode else player1Mode) match {
      case SetModeState() => set((row, col))
      case RemoveModeState() => remove((row, col))
      case MoveModeState() => move((row, col))
      case FlyModeState() => fly((row, col))
    }).checkWinner().modeChoice()
  }

  def modeChoice(placedStones: (Int, Int) = (field.placedBlackStones(), field.placedWhiteStones())): RoundManager = {
    val mgr: RoundManager = copy()
    var player1Mode = mgr.player1Mode
    var player2Mode = mgr.player2Mode
    val roundCounter = mgr.roundCounter
    val (placedBlackStones, placedWhiteStones) = placedStones

    if (field.millState != MillState.handle(NoMillState())) {
      MillState.whichState(field.millState) match {
        case WhiteMillState() => player1Mode = ModeState.handle(RemoveModeState())
        case BlackMillState() => player2Mode = ModeState.handle(RemoveModeState())
      }
    } else if (roundCounter < borderToMoveMode) {
      player1Mode = ModeState.handle(SetModeState())
      player2Mode = ModeState.handle(SetModeState())
      if (roundCounter == borderToMoveMode - 1) {
        player1Mode = ModeState.handle(MoveModeState())
      }
    } else if (placedBlackStones == 3 || placedWhiteStones == 3) {
      player1Mode = ModeState.handle(MoveModeState())
      player2Mode = ModeState.handle(MoveModeState())
      if (placedWhiteStones == 3) {
        player1Mode = ModeState.handle(FlyModeState())
      }
      if (placedBlackStones == 3) {
        player2Mode = ModeState.handle(FlyModeState())
      }
    } else {
      player1Mode = ModeState.handle(MoveModeState())
      player2Mode = ModeState.handle(MoveModeState())
    }
    copy(player1Mode = player1Mode, player2Mode = player2Mode)
  }

  private def updateSingleCell(): RoundManager = copy(update = (-1, -1))

  private def updateAllCells(): RoundManager = copy(update = (-2, -2))

  private def set(cell: (Int, Int)): RoundManager = {
    val mgr: RoundManager = copy()
    var field: FieldInterface = mgr.field
    var roundCounter: Int = mgr.roundCounter
    val (row, col): (Int, Int) = cell

    if (field.available(row, col) && field.millState == "No Mill") {
      // set cell normally
      val (rField, rSuccessfullyRemoved) = field.set(row, col, if (mgr.blackTurn()) Cell("cb") else Cell("cw"))
      if (rSuccessfullyRemoved) {
        field = rField.checkMill(row, col)
        if (field.millState == "No Mill") {
          roundCounter += 1
        }
      }
    }
    copy(field = field, roundCounter = roundCounter).updateSingleCell()
  }

  private def remove(cell: (Int, Int)): RoundManager = {
    val mgr: RoundManager = copy()
    var field: FieldInterface = mgr.field
    var roundCounter: Int = mgr.roundCounter
    val (row, col): (Int, Int) = cell
    var update: Int = 0

    val cellColor: Color.Value = field.cell(row, col).content.color
    if ((cellColor == Color.black && mgr.whiteTurn()) || (cellColor == Color.white && mgr.blackTurn())) {
      val ignore: Boolean = field.placedBlackStones() <= 3 && field.placedWhiteStones() <= 3
      val (rField, rSuccessfullyRemoved) = field.removeStone(row, col, ignore)
      if (rSuccessfullyRemoved) {
        field = rField.resetMill()
        roundCounter += 1
        update = 2
      }
    }
    copy(field = field, roundCounter = roundCounter).updateSingleCell()
  }

  private def checkIfCanMove(): Boolean = {
    // checks if the player has a cell where it can move to
    // if not it returns false
    if ((whiteTurn() && player1Mode == "MoveMode") || (blackTurn() && player2Mode == "MoveMode")) {
      val playerColor: Color.Value = if (blackTurn()) Color.black else Color.white
      field.cellsWithIndex
        .filter(c => c._2.content.color == playerColor)
        .exists(c => field.checkIfCanMove(c._1._1, c._1._2))
    } else {
      true
    }
  }

  private def checkIfHasOnly3Stones(): Boolean = {
    if (blackTurn() && player2Mode != "SetMode") {
      field.placedBlackStones() < 3
    } else if (whiteTurn() && player1Mode != "SetMode") {
      field.placedWhiteStones() < 3
    } else {
      false
    }
  }

  private def checkWinner(): RoundManager = {
    if (checkIfHasOnly3Stones() || !checkIfCanMove()) {
      val winner = if (blackTurn()) 2 else 1
      copy(winner = winner, winnerText = handleWinnerText(winner))
    } else {
      copy()
    }
  }

  private def move(cell: (Int, Int)): RoundManager = moveOrFly(cell, field.moveStone)

  private def fly(cell: (Int, Int)): RoundManager = moveOrFly(cell, field.fly)

  private def moveOrFly(cell: (Int, Int), changeStone: (Int, Int, Int, Int) => (FieldInterface, Boolean)): RoundManager = {
    val mgr: RoundManager = copy()
    var field: FieldInterface = mgr.field
    var roundCounter: Int = mgr.roundCounter

    val (row, col): (Int, Int) = cell
    var tmpCell@(tmpRow, tmpCol): (Int, Int) = mgr.tmpCell

    val cellColor = field.cell(row, col).content.color
    var update: Int = 0

    // do move only if it's own color and destination field has no color!!
    if (tmpCell != (-1, -1) && tmpCell != cell && cellColor == Color.noColor) {
      val (rField, rSuccessfullyMoved) = changeStone(tmpRow, tmpCol, row, col)
      if (rSuccessfullyMoved) {
        field = rField.checkMill(row, col)
        if (field.millState == "No Mill") {
          roundCounter += 1
          tmpCell = (-1, -1) // reset temporary cell after move
          update = 2
        }
      }
    }
    else {
      // update temporary cell
      if ((cellColor == Color.black && mgr.blackTurn()) ||
        (cellColor == Color.white && mgr.whiteTurn())) {
        tmpCell = cell
      }
    }
    copy(field = field, roundCounter = roundCounter, tmpCell = tmpCell, update = mgr.tmpCell)
  }

  def updateToJson(): String = Json.prettyPrint(
    Json.obj(
      "row" -> update._1,
      "col" -> update._2
    ))

  @deprecated
  def handleWinnerText(winner: Int = winner): String = {
    winner match {
      case 0 => "No Winner"
      case 1 => "White wins!"
      case 2 => "Black wins!"
    }
  }
}
