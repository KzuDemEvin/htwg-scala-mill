package de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Injector}
import de.htwg.se.mill.MillModule
import de.htwg.se.mill.controller.controllerComponent.{FlyModeState, ModeState, MoveModeState, SetModeState}
import de.htwg.se.mill.model.fieldComponent.FieldInterface
import de.htwg.se.mill.model.playerComponent.Player

case class RoundManager()  {
  var roundCounter = 0
  val borderToMoveMode = 18
  val injector: Injector = Guice.createInjector(new MillModule)
  var player1: Player = Player(name = "No Name1")
  var player2: Player = Player(name = "No Name2")
  var winner = 0
  var winnerText = "No Winner"

  def blackTurn():Boolean = roundCounter % 2 == 1

  def whiteTurn():Boolean = roundCounter % 2 == 0

  def modeChoice(field:FieldInterface): Unit = {
    if (roundCounter < borderToMoveMode) {
      player1 = player1.changeMode(ModeState.handle(SetModeState()))
      player2 = player2.changeMode(ModeState.handle(SetModeState()))
      if (roundCounter == borderToMoveMode - 1) {
        player1 = player1.changeMode(ModeState.handle(MoveModeState()))
      }
    } else if (field.placedBlackStones() == 2) {
      winner = 1
      handleWinnerText(1)
    } else if (field.placedWhiteStones() == 2) {
      winner = 2
      handleWinnerText(2)
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
  }

  def selectDriveCommand():ModeState = {
    var cmd = ModeState.whichState(SetModeState().handle)
    if (blackTurn()) {
      cmd = ModeState.whichState(player2.mode)
    } else {
      cmd = ModeState.whichState(player1.mode)
    }
    cmd
  }

  def handleWinnerText(winner:Int): Unit = {
    winner match {
      case 0 => winnerText = "No Winner"
      case 1 => winnerText = player1.name + " wins (White) !"
      case 2 => winnerText = player2.name + " wins (Black) !"
    }
  }
}
