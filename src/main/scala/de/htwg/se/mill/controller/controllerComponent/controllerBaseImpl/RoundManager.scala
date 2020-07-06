package de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.mill.controller.controllerComponent.{FlyModeState, ModeState, MoveModeState, SetModeState}
import de.htwg.se.mill.model.fieldComponent.FieldInterface
import de.htwg.se.mill.model.playerComponent.PlayerInterface

case class RoundManager() {
  var roundCounter = 0
  val borderToMoveMode = 18
  val player1 = Player("Kevin")
  val player2 = Player("Manuel")

  def blackTurn():Boolean = if (roundCounter % 2 == 0) true else false

  def whiteTurn():Boolean = if (roundCounter % 2 == 1) true else false

  def modeChoice(field:FieldInterface): Unit = {
    if (roundCounter < borderToMoveMode) {
      player1.mode = ModeState.handle(SetModeState())
      player2.mode = ModeState.handle(SetModeState())
      if (roundCounter == borderToMoveMode - 1) {
        player1.mode = ModeState.handle(MoveModeState())
      }
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
      cmd = ModeState.whichState(player1.mode)
    } else {
      cmd = ModeState.whichState(player2.mode)
    }
    cmd
  }

}
