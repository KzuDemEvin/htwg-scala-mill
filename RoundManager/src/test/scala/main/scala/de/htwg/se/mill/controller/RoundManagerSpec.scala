package main.scala.de.htwg.se.mill.controller

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.mill.controller.controllerComponent.{FlyModeState, ModeState, MoveModeState, SetModeState}
import de.htwg.se.mill.model.RoundManager
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Field
import org.scalatest.{Matchers, WordSpec}

class RoundManagerSpec extends WordSpec with Matchers {
  "A RoundManager" when {
    val normalSize = 7
    "created" should {
      val roundManager = new RoundManager
      "handle the WinnerText" in {
        roundManager.handleWinnerText(0) should be("No Winner")
        roundManager.handleWinnerText(1) should be(roundManager.player1.name + " wins (White) !")
        roundManager.handleWinnerText(2) should be(roundManager.player2.name + " wins (Black) !")
      }
      "select the right drive command when both players have SetMode and its whites turn" in {
        val field = new Field(normalSize)
        val controller = new Controller(field)
        //controller.handleClick(0,0)
        roundManager.player1.mode should be(ModeState.handle(SetModeState()))
        roundManager.selectDriveCommand() should be(ModeState.whichState(SetModeState().handle))
      }
      "select the right drive command when both players have SetMode and its's blacks turn" in {
        val field = new Field(normalSize)
        val controller = new Controller(field)
        controller.handleClick(0,0)
        roundManager.player2.mode should be(ModeState.handle(SetModeState()))
        roundManager.selectDriveCommand() should be(ModeState.whichState(SetModeState().handle))
      }
      "choose the correct mode per player" should {
        val field = new Field(normalSize)
        val controller = new Controller(field)
        "both should be in Set Mode" in {
          controller.mgr.player1.mode should be(ModeState.handle(SetModeState()))
          controller.mgr.player2.mode should be(ModeState.handle(SetModeState()))
        }
        "both should be in Move Mode" in {
          controller.handleClick(0,0)
          controller.handleClick(6,0)
          controller.handleClick(0,3)
          controller.handleClick(6,3)
          controller.handleClick(0,6)
          controller.handleClick(6,3) //Remove
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
          controller.mgr.player1Mode should be(ModeState.handle(MoveModeState()))
          controller.mgr.player2Mode should be(ModeState.handle(MoveModeState()))
        }
        "player 1 in flymode" in {
          controller.handleClick(1,3) ; controller.handleClick(0,3)
          controller.handleClick(6,3) ; controller.handleClick(5,3)
          controller.handleClick(0,6) //remove
          controller.mgr.player1Mode should be(ModeState.handle(FlyModeState()))
          controller.mgr.player2Mode should be(ModeState.handle(MoveModeState()))
        }
        "both in fly mode" in {
          controller.handleClick(0,3) ; controller.handleClick(1,3)
          controller.handleClick(6,6) //remove
          controller.handleClick(6,0) ; controller.handleClick(3,0)
          controller.handleClick(1,3) ; controller.handleClick(0,3)
          controller.handleClick(3,0) ; controller.handleClick(6,0)
          controller.handleClick(0,3) ; controller.handleClick(1,3)
          controller.handleClick(6,0) //remove
          controller.mgr.player1Mode should be(ModeState.handle(FlyModeState()))
          controller.mgr.player2Mode should be(ModeState.handle(FlyModeState()))
        }
      }
    }
  }

}
