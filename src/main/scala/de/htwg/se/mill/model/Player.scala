package de.htwg.se.mill.model

case class Player(name: String, amountStones: Int = 9) {
   def this (name: String) {
      this(name, 9)
   }
   override def toString:String = "Name: " + name + ", Amount of Stones: " + amountStones
}

