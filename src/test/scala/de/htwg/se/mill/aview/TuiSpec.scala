package de.htwg.se.mill.aview

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.mill.model.fieldComponent.Color
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.Field
import org.scalatest.{Matchers, WordSpec}

class TuiSpec extends WordSpec with Matchers {

  "A Mill Tui" should {
    val controller = new Controller(new Field(7))
    val tui = new Tui(controller)
    "create an empty Mill on input 'new'" in {
      tui.execInput("new")
      controller.field.placedStones() should be(0)
    }
    "set a black stone on input '00'" in {
      val row = 0
      val col = 0
      tui.execInput(s"$row$col")
      controller.field.available(row, col) should be(false)
      controller.cell(row, col).content.color should be(Color.white)
    }
    "set a white stone on input '03" in {
      val row = 0
      val col = 3
      tui.execInput(s"$row$col")
      controller.field.available(row, col) should be(false)
      controller.field.cell(row, col).content.color should be(Color.black)
    }
    "undo on input 'undo'" in {
      val row = 6
      val col = 6
      tui.execInput(s"$row$col")
      tui.execInput("undo")
      controller.cell(row, col).isSet should be (false)
    }
    "redo on input 'redo'" in {
      val row = 0
      val col = 0
      tui.execInput(s"$row$col")
      tui.execInput("undo")
      tui.execInput("redo")
      controller.cell(row,col).isSet should be(true)
    }
    "place 18 random stones" in {
      tui.execInput("random")
      controller.field.placedStones() should be(18)
    }
  }
}
