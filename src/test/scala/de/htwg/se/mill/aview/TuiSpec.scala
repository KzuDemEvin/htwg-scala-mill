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
      controller.field should be(new Field(7))
    }
    "place 6 random stones on " in {
      tui.execInput("random")
      controller.field.placedStones should be(6)
    }
    "set a white stone on 0, 0" in {
      tui.execInput("white")
      controller.field.available(0, 0) should be(false)
    }
    "set a black stone on 1, 1" in {
      tui.execInput("black")
      controller.field.available(1, 1) should be(false)
    }
    "set a white stone on input 'white'" in {
      tui.execInput("white")
      controller.field.cell(0,0).content.whichColor should be(Color.white)
    }
    "do nothing on bad input like'something'" in {
      val old = controller.fieldToString
      tui.execInput("something")
      controller.fieldToString should be(old)
    }
  }
}
