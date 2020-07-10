package de.htwg.se.mill.controller

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.RoundManager
import org.scalatest.{Matchers, WordSpec}

class RoundManagerSpec extends WordSpec with Matchers {
  "A RoundManager" when {
    "created" should {
      val roundManager = new RoundManager
      "handle the WinnerText" in {
        roundManager.handleWinnerText(0)
        roundManager.winnerText should be("No Winner")

        roundManager.handleWinnerText(1)
        roundManager.winnerText should be(roundManager.player1.name + " wins (White) !")

        roundManager.handleWinnerText(2)
        roundManager.winnerText should be(roundManager.player2.name + " wins (Black) !")
      }
    }
  }

}
