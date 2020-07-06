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
      tui.execInput("00")
      controller.field.available(0, 0) should be(false)
      controller.cell(0, 0).getContent.whichColor should be(Color.white)
    }
    "set a white stone on input '03" in {
      tui.execInput("03")
      controller.field.available(0, 3) should be(false)
      controller.field.cell(0, 3).getContent.whichColor should be(Color.black)
    }
//    "move a white stone from '03' to '13'" in {
//      tui.execInput("0313")
//      controller.field.available(1, 3) should be(true)
//      controller.field.cell(1, 3).getContent.whichColor should be(Color.black)
//    }
    "undo on input 'undo'" in {
      tui.execInput("66")
      tui.execInput("undo")
      controller.cell(6, 6).isSet should be (false)
    }
    "redo on input 'redo'" in {
      tui.execInput("00")
      tui.execInput("undo")
      tui.execInput("redo")
      controller.cell(0,0).isSet should be(true)
    }
    "place 18 random stones" in {
      tui.execInput("random")
      controller.field.placedStones() should be(18)
    }
//    "do nothing on bad input like'something'" in {
//      val old = controller.fieldToString
//      tui.execInput("something")
//      controller.fieldToString should be(old)
//    }
  }
}
