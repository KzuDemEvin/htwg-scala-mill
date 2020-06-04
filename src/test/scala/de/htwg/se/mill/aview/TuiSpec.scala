package de.htwg.se.mill.aview

import de.htwg.se.mill.controller.Controller
import de.htwg.se.mill.model.Field

import org.scalatest.{Matchers, WordSpec}

class TuiSpec extends WordSpec with Matchers{

  "A Mill Tui" should {
    val controller = new Controller(new Field(7))
    val tui = new Tui(controller)
    "create an empty Mill on input 'new'" in {
      tui.execInput("new")
      controller.field should be(new Field(7))
    }
    "set a white stone on 0, 0" in {
      tui.execInput("white")
      controller.field.available(0, 0) should be(false)
    }
    "set a black stone on 1, 1" in {
      tui.execInput("black")
      controller.field.available(1, 1) should be(false)
    }
    "throw an exception when something weird is in input" in {
      an [IllegalArgumentException] should be thrownBy(tui.execInput("something weird"))
    }
//    "set a cell on input '123'" in {
//      tui.execInput("123")
//      controller.grid.cell(1,2).value should be(3)
//    }
//    "create a random Sudoku on input 'r'" in {
//      tui.processInputLine("r")
//      controller.grid.valid should be(true)
//    }
//    "solve a Sudoku on input 's'" in {
//      tui.processInputLine("n")
//      tui.processInputLine("s")
//      controller.grid.solved should be(true)
//    }
  }
}
