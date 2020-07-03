package de.htwg.se.mill.model.playerComponent

import de.htwg.se.mill.controller.controllerComponent.{ModeState, SetModeState}

case class Player(name: String, amountStones: Int = 9) {
   def this (name: String) {
      this(name, 9)
   }

   var mode = ModeState.handle(SetModeState())
   override def toString:String = "Name: " + name + ", Amount of Stones: " + amountStones
}
