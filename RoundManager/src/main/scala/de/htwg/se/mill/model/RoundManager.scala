package de.htwg.se.mill.model

import de.htwg.se.mill.controller.controllerComponent.{FlyModeState, ModeState, MoveModeState, SetModeState}

case class RoundManager(player1Mode: String = "SetMode",
                        player2Mode: String = "SetMode",
                        setCounter: Int = 0,
                        moveCounter: Int = 0,
                        flyCounter: Int = 0,
                        roundCounter: Int = 0,
                        borderToMoveMode: Int = 18,
                        winner: Int = 0,
                        winnerText: String = "No Winner") {

  def blackTurn(): Boolean = roundCounter % 2 == 1

  def whiteTurn(): Boolean = roundCounter % 2 == 0

  def setPlayerMode(playerMode: String, number: Int = 1): RoundManager = {
    if (number == 1) {
      copy(player1Mode = playerMode)
    } else {
      copy(player2Mode = playerMode)
    }
  }

  def modeChoice(placedStones: (Int, Int)): RoundManager = {
    val mgr = copy()
    var player1Mode = mgr.player1Mode
    var player2Mode = mgr.player2Mode
    var winner = mgr.winner
    var winnerText = mgr.winnerText
    val roundCounter = mgr.roundCounter
    val (placedBlackStones, placedWhiteStones) = placedStones

    if (roundCounter < borderToMoveMode) {
      player1Mode = ModeState.handle(SetModeState())
      player2Mode = ModeState.handle(SetModeState())
      if (roundCounter == borderToMoveMode - 1) {
        player1Mode = ModeState.handle(MoveModeState())
      }
    } else if (placedBlackStones == 2) {
      winner = 1
      winnerText = handleWinnerText(winner)
    } else if (placedWhiteStones == 2) {
      winner = 2
      winnerText = handleWinnerText(winner)
    } else if (placedBlackStones == 3 || placedWhiteStones == 3) {
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
    copy(player1Mode = player1Mode, player2Mode = player2Mode, winner = winner, winnerText = winnerText)
  }

  def selectDriveCommand(): ModeState = {
    ModeState.whichState(if (blackTurn()) player2Mode else player1Mode)
  }

  def handleWinnerText(winner: Int = winner): String = {
    winner match {
      case 0 => "No Winner"
      case 1 => "White wins!"
      case 2 => "Black wins!"
    }
  }
}
