package de.htwg.se.mill.model.playerComponent

import de.htwg.se.mill.controller.controllerComponent.{ModeState, SetModeState}

trait Player {
   val name:String
   val amountStones:Int
   var mode:String
}

private class ClassicPlayer(override val name: String, override val amountStones: Int = 9) extends Player {
   def this (name: String) = {
      this(name, 9)
   }

   var mode = ModeState.handle(SetModeState())
   override def toString:String = "Name: " + name + ", Amount of Stones: " + amountStones
}

object Player {
   def apply(name:String): Player = new ClassicPlayer(name)
   def apply(name:String, amountStones:Int):Player = new ClassicPlayer(name, amountStones)
}

