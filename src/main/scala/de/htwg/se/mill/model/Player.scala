package de.htwg.se.mill.model

case class Player(val name: String, val amountStones: Int) {
   val noMagicNumber = 9
   def this (name: String) {
      this(name, noMagicNumber)
   }

   override def toString:String = {
      "Name: " + name + ", Amount of Stones: " + amountStones
   }
}

