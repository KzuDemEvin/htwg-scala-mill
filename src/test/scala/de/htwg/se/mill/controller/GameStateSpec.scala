package de.htwg.se.mill.controller

import de.htwg.se.mill.controller.controllerComponent.{BlackTurnState, FinishedState, GameState, LoadState, NewState, SaveState, WhiteTurnState}
import org.scalatest.{Matchers, WordSpec}

class GameStateSpec extends WordSpec with Matchers {
  "A GameState" when {
    "should represent the game current sate. It" should {
      "be in progress" in {
        val gameStateProgress = GameState.handle(NewState())
        gameStateProgress should be("New field")
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
      "say its in Save State" in {
        val gameStateSave = GameState.handle(SaveState())
        gameStateSave should be("Game saved")
      }
      "say its in Load State" in {
        val gameStateLoad = GameState.handle(LoadState())
        gameStateLoad should be("Game loaded")
      }
    }

  }

}
