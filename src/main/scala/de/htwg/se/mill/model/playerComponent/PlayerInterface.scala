package de.htwg.se.mill.model.playerComponent

import de.htwg.se.mill.controller.controllerComponent.ModeState

trait PlayerInterface {
  val name:Int
  val amountStones:Int
  var mode:ModeState
}