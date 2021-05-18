package de.htwg.se.mill.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ModeStateSpec extends AnyWordSpec with Matchers {
  "SetModeState" when {
    "initalized" should {
      "be of type MoveModeState" in {
        val setModeState = SetModeState()
        setModeState.isInstanceOf[ModeState] should be(true)
        setModeState.handle should be("SetMode")
      }
    }
  }
  "RemoveModeState" when {
    "initalized" should {
      "be of type MoveModeState" in {
        val removeModeState = RemoveModeState()
        removeModeState.isInstanceOf[ModeState] should be(true)
        removeModeState.handle should be("RemoveMode")
      }
    }
  }
  "MoveModeState" when {
    "initalized" should {
      "be of type MoveModeState" in {
        val moveModeState = MoveModeState()
        moveModeState.isInstanceOf[ModeState] should be(true)
        moveModeState.handle should be("MoveMode")
      }
    }
  }
  "FlyModeState" when {
    "initalized" should {
      "be of type MoveModeState" in {
        val flyModeState = FlyModeState()
        flyModeState.isInstanceOf[ModeState] should be(true)
        flyModeState.handle should be("FlyMode")
      }
    }
  }
}
