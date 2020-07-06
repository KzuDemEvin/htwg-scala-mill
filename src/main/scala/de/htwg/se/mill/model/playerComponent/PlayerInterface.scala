package de.htwg.se.mill.model.playerComponent

import de.htwg.se.mill.controller.controllerComponent.ModeState

trait PlayerInterface {
  val name:String
  val amountStones:Int
  var mode:String
}