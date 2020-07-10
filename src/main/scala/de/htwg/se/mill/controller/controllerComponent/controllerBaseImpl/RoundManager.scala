package de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject, Injector}
import com.google.inject.name.Names
import de.htwg.se.mill.Mill.injector
import de.htwg.se.mill.MillModule
import de.htwg.se.mill.controller.controllerComponent.{ControllerInterface, FlyModeState, ModeState, MoveModeState, SetModeState}
import de.htwg.se.mill.model.fieldComponent.FieldInterface
import de.htwg.se.mill.model.playerComponent.Player

case class RoundManager @Inject() ()  {
  var roundCounter = 0
  val borderToMoveMode = 18
  val injector: Injector = Guice.createInjector(new MillModule)
  var player1 = Player("No Name1")
  var player2 = Player("No Name2")
  var winner = 0
  var winnerText = "No Winner"

  def blackTurn():Boolean = if (roundCounter % 2 == 1) true else false

  def whiteTurn():Boolean = if (roundCounter % 2 == 0) true else false

  def modeChoice(field:FieldInterface): Unit = {
    if (roundCounter < borderToMoveMode) {
      player1.mode = ModeState.handle(SetModeState())
      player2.mode = ModeState.handle(SetModeState())
      if (roundCounter == borderToMoveMode - 1) {
        player1.mode = ModeState.handle(MoveModeState())
      }
    } else if (field.placedBlackStones() == 2) {
      winner = 1
      handleWinnerText(1)
    } else if (field.placedWhiteStones() == 2) {
      winner = 2
      handleWinnerText(2)
    } else if (field.placedBlackStones() == 3 || field.placedWhiteStones() == 3) {
      if (field.placedWhiteStones() == 3) {
        player1.mode = ModeState.handle(FlyModeState())
      }
      if (field.placedBlackStones() == 3) {
        player2.mode = ModeState.handle(FlyModeState())
      }
    } else {
      player1.mode = ModeState.handle(MoveModeState())
      player2.mode = ModeState.handle(MoveModeState())
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
