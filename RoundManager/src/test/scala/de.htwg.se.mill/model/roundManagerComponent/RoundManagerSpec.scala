package de.htwg.se.mill.model.roundManagerComponent

import de.htwg.se.mill.controller.controllerRoundManager.RoundManagerController
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Field
import de.htwg.se.mill.model.{FlyModeState, MoveModeState, SetModeState}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class RoundManagerSpec extends AnyWordSpec with Matchers {
  "A RoundManager" when {
    val normalSize = 7
    "created" should {
      val roundManager = new RoundManager()
      "choose the correct mode per player" should {
        val field = new Field(normalSize)
        val controller = new RoundManagerController(field)
        "both should be in SetMode" in {
          controller.mgr.player1Mode should be(SetModeState())
          controller.mgr.player2Mode should be(SetModeState())
        }
        "player 1 should be in MoveMode and player 2 in RemoveMode" in {
          controller.handleClick(0,0)
          controller.mgr.blackTurn() should be(true)

          controller.handleClick(6,0)
          controller.mgr.whiteTurn() should be(true)

          controller.handleClick(0,3)
          controller.handleClick(6,3)
          controller.handleClick(0,6)
          controller.mgr.field.millState should be("White Mill")

          controller.handleClick(6,3) // white Remove
          controller.handleClick(6,3)
          controller.handleClick(1,1)
          controller.handleClick(6,6)
          controller.handleClick(1,1) //Remove
          controller.handleClick(1,1)
          controller.handleClick(5,1)
          controller.handleClick(1,3)
          controller.handleClick(5,3)
          controller.handleClick(1,5)
          controller.handleClick(5,3) //remove
          controller.handleClick(5,3)
          controller.handleClick(2,2)
          controller.handleClick(5,5)
          controller.handleClick(2,2) //remove
          controller.handleClick(2,2)
          controller.handleClick(4,2)
          controller.handleClick(2,2) ; controller.handleClick(2,3)
          controller.handleClick(4,2)
          controller.handleClick(5,3) ; controller.handleClick(4,3)
          controller.handleClick(2,3) ; controller.handleClick(2,2)
          controller.handleClick(4,3) ; controller.handleClick(5,3)
          controller.handleClick(2,2) //remove
          controller.handleClick(1,3) ; controller.handleClick(2,3)
          controller.handleClick(5,3) ; controller.handleClick(4,3)
          controller.handleClick(2,3) ; controller.handleClick(2,2)
          controller.handleClick(6,3) ; controller.handleClick(5,3)
          controller.handleClick(2,2) //remove
          controller.handleClick(0,3) ; controller.handleClick(1,3)
          controller.handleClick(4,3) //remove
          controller.handleClick(5,3) ; controller.handleClick(6,3)
          controller.handleClick(0,0) //remove
          controller.mgr.player1Mode should be(MoveModeState())
          controller.mgr.player2Mode should be(MoveModeState())
        }
        "player 1 in FlyMode" in {
          controller.handleClick(1,3) ; controller.handleClick(0,3)
          controller.handleClick(6,3) ; controller.handleClick(5,3)
          controller.handleClick(0,6) //remove
          controller.mgr.player1Mode should be(FlyModeState())
          controller.mgr.player2Mode should be(MoveModeState())
        }
        "both in fly mode" in {
          controller.handleClick(0,3) ; controller.handleClick(1,3)
          controller.handleClick(6,6) //remove
          controller.handleClick(6,0) ; controller.handleClick(3,0)
          controller.handleClick(1,3) ; controller.handleClick(0,3)
          controller.handleClick(3,0) ; controller.handleClick(6,0)
          controller.handleClick(0,3) ; controller.handleClick(1,3)
          controller.handleClick(6,0) //remove
          controller.mgr.player1Mode should be(FlyModeState())
          controller.mgr.player2Mode should be(FlyModeState())
        }
      }
    }
  }

}
