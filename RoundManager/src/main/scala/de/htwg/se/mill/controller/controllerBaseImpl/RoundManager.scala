package de.htwg.se.mill.controller.controllerBaseImpl

import de.htwg.se.mill.controller.controllerComponent.{FlyModeState, ModeState, MoveModeState, SetModeState}
import de.htwg.se.mill.model.fieldComponent.FieldInterface
import de.htwg.se.mill.model.playerComponent.Player

case class RoundManager(player1: Player,
                        player2: Player,
                        roundCounter: Int = 0,
                        borderToMoveMode: Int = 18,
                        winner: Int = 0,
                        winnerText: String = "No Winner") {

  def this() {
    this(
      player1 = Player(name = "No Name1"),
      player2 = Player(name = "No Name2")
    )
  }

  def blackTurn(): Boolean = roundCounter % 2 == 1

  def whiteTurn(): Boolean = roundCounter % 2 == 0

  def setPlayer(player: Player, number: Int = 1): RoundManager = {
    if (number == 1) {
      copy(player1 = player)
    } else {
      copy(player2 = player)
    }
  }

  def modeChoice(field: FieldInterface): RoundManager = {
    val mgr = copy()
    var player1 = mgr.player1
    var player2 = mgr.player2
    var winner = mgr.winner
    var winnerText = mgr.winnerText
    val roundCounter = mgr.roundCounter
    if (roundCounter < borderToMoveMode) {
      player1 = player1.changeMode(ModeState.handle(SetModeState()))
      player2 = player2.changeMode(ModeState.handle(SetModeState()))
      if (roundCounter == borderToMoveMode - 1) {
        player1 = player1.changeMode(ModeState.handle(MoveModeState()))
      }
    } else if (field.placedBlackStones() == 2) {
      winner = 1
      winnerText = handleWinnerText(winner)
    } else if (field.placedWhiteStones() == 2) {
      winner = 2
      winnerText = handleWinnerText(winner)
    } else if (field.placedBlackStones() == 3 || field.placedWhiteStones() == 3) {
      if (field.placedWhiteStones() == 3) {
        player1 = player1.changeMode(ModeState.handle(FlyModeState()))
      }
      if (field.placedBlackStones() == 3) {
        player2 = player2.changeMode(ModeState.handle(FlyModeState()))
      }
    } else {
      player1 = player1.changeMode(ModeState.handle(MoveModeState()))
      player2 = player2.changeMode(ModeState.handle(MoveModeState()))
    }
    copy(player1 = player1, player2 = player2, winner = winner, winnerText = winnerText)
  }

  def selectDriveCommand(): ModeState = {
    ModeState.whichState(if (blackTurn()) player2.mode else player1.mode)
  }

  def handleWinnerText(winner: Int = winner): String = {
    winner match {
      case 0 => "No Winner"
      case 1 => player1.name + " wins (White) !"
      case 2 => player2.name + " wins (Black) !"
    }
  }
}