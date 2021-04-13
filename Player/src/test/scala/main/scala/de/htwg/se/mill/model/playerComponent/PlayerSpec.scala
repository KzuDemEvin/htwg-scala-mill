package de.htwg.se.mill.model.playerComponent

import de.htwg.se.mill.model.playerComponent.Player
import org.scalatest._

class PlayerSpec extends WordSpec with Matchers {

  "A Player" when {
    "new" should {
      "with extra specification of amount of stones" should {
        val player2 = Player.apply("Manuel", 9)
        "have a name" in {
          player2.name should be("Manuel")
        }
        "should start with 9 stones" in {
          player2.amountStones should be(9)
        }
        "should have a nice String representation" in {
          player2.toString should be("Name: Manuel, Amount of Stones: 9")
        }
      }
      "by default" should {
        val player1 = Player.apply("Kevin")
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
