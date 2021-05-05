package de.htwg.se.mill.controller

import de.htwg.se.mill.controller.controllerComponent._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameStateSpec extends AnyWordSpec with Matchers {
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
      "say its in Undo State" in {
        val gameStateLoad = GameState.handle(UndoState())
        gameStateLoad should be("Undo")
      }
      "say its in Redo State" in {
        val gameStateLoad = GameState.handle(RedoState())
        gameStateLoad should be("Redo")
      }
    }

  }

}
