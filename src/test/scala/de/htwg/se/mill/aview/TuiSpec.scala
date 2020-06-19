package de.htwg.se.mill.aview

import de.htwg.se.mill.controller.Controller
import de.htwg.se.mill.model.{Color, Field}
import org.scalatest.{Matchers, WordSpec}

class TuiSpec extends WordSpec with Matchers{

  "A Mill Tui" should {
    val controller = new Controller(new Field(7))
    val tui = new Tui(controller)
    "create an empty Mill on input 'new'" in {
      tui.execInput("new")
      controller.field.placedStones() should be(0)
    }
    "place 24 random stones" in {
      tui.execInput("random")
      controller.field.placedStones() should be(24)
    }
    "set a white stone on input 'place 000'" in {
      tui.execInput("place 000")
      controller.field.available(0, 0) should be(false)
      controller.field.cell(0, 0).content.whichColor should be(Color.white)
    }
    "set a black stone on input 'place 031" in {
      tui.execInput("place 031")
      controller.field.available(0, 3) should be(false)
      controller.field.cell(0, 3).content.whichColor should be(Color.black)
    }
    "set a white stone on input 'white'" in {
      tui.execInput("place 000")
      controller.field.cell(0,0).content.whichColor should be(Color.white)
    }
    "redo on input 'redo'" in {
      tui.execInput("place 000")
      tui.execInput("undo")
      tui.execInput("redo")
      controller.field.cell(0,0).isSet should be(true)
    }
    "do nothing on bad input like'something'" in {
      val old = controller.fieldToString
      tui.execInput("something")
      controller.fieldToString should be(old)
    }
  }
}
