package de.htwg.se.mill.model

import org.scalatest._

class PlayerSpec extends WordSpec with Matchers {
  val player1 = Player("Kevin")

  "A Player" when {
    "new" should {
      "have a name" in {
        player1.name should be("Kevin")
      }
      "should start with 9 stones" in {
        player1.amountStones should be(9)
      }
    }
  }

  "At each time a Player" should {
   "have a nice String representation" in {
     player1.toString should be("Name: Kevin, Amount of Stones: 9")
   }
  }
}
