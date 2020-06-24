package de.htwg.se.mill.controller

import org.scalatest.{Matchers, WordSpec}

class GameStateSpec extends WordSpec with Matchers {
  "A GameState" when {
    "should represent the game current sate. It" should {
      "be in progress" in {
        val gameStateProgress = GameState.handle(InProgessState())
        gameStateProgress should be("Game in progress")
      }
      "be finished" in {
        val gameStateFinished = GameState.handle(FinishedState())
        gameStateFinished should be("Game finished")
      }
      "say its white's turn" in {
        val gameStateWhite = GameState.handle(WhiteTurnState())
        gameStateWhite should be("White's turn")
      }
      "say its black's turn" in {
        val gameStateBlack = GameState.handle(BlackTurnState())
        gameStateBlack should be("Black's turn")
      }
    }

  }

}
