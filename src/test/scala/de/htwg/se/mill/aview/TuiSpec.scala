package de.htwg.se.mill.aview

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.Controller
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TuiSpec extends AnyWordSpec with Matchers {

  "A Mill Tui" should {
    val controller = new Controller
    val tui = new Tui(controller)
    "create an empty Mill on input 'new'" in {
      tui.execInput("new")
      controller.fieldsize should be(7)
    }
    "set a white stone on input '00'" in {
      val row = 0
      val col = 0
      tui.execInput(s"$row$col")
      controller.isSet(row, col)({ case Some(isSet) => isSet.toBoolean should be(true)})
      controller.color(row, col)({ case Some(color) => color should be("White")})
    }
    "set a black stone on input '03" in {
      val row = 0
      val col = 3
      tui.execInput(s"$row$col")
      controller.isSet(row, col)({ case Some(isSet) => isSet.toBoolean should be(true)})
      controller.color(row, col)({ case Some(color) => color should be("Black")})
    }
    "undo on input 'undo'" in {
      val row = 6
      val col = 6
      tui.execInput(s"$row$col")
      tui.execInput("undo")
      // controller.isSet(row, col) should be (false)
    }
    "redo on input 'redo'" in {
      val row = 0
      val col = 0
      tui.execInput(s"$row$col")
      tui.execInput("undo")
      tui.execInput("redo")
      // controller.isSet(row, col) should be(true)
    }
    "place 18 random stones" in {
      tui.execInput("random")
      // controller.mgr.field.placedStones() should be(18)
    }
  }
}
