package de.htwg.se.mill.model

import org.scalatest._

class PlayerSpec extends WordSpec with Matchers {


  "A Player" when {
    "new" should {
      "with extra specification of amount of stones" should {
        val player2 = Player("Manuel", 8)
        "have a name" in {
          player2.name should be("Manuel")
        }
        "should start with 9 stones" in {
          player2.amountStones should be(8)
        }
        "should have a nice String representation" in {
          player2.toString should be("Name: Manuel, Amount of Stones: 8")
        }
      }
      "by default" should {
        val player1 = new Player("Kevin")
        "have a name" in {
          player1.name should be("Kevin")
        }
        "should start with 9 stones" in {
          player1.amountStones should be(9)
        }
        "should have a nice String representation" in {
          player1.toString should be("Name: Kevin, Amount of Stones: 9")
        }
      }

    }
  }

}
