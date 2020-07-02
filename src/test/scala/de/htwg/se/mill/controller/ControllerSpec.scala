package de.htwg.se.mill.controller

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.mill.model.fieldComponent.fieldBaseImpl.{Color, Field, Stone}
import de.htwg.se.mill.util.Observer

import scala.language.reflectiveCalls
import org.scalatest.{Matchers, WordSpec}

class ControllerSpec extends WordSpec with Matchers {
  "A Controller" when {
    "observed by an Observer" should {
      val Field = new Field(7)
      val controller = new Controller(Field)
      val observer = new Observer {
        var updated: Boolean = false

        def isUpdated: Boolean = updated

        override def update: Boolean = {
          updated = true;
          updated
        }
      }
    }

//      controller.add(observer)
//      "notify its Observer after creation" in {
//        controller.createEmptyField(7)
//        observer.updated should be(true)
//        controller.field.size should be(7)
//      }
//      "notify its Observer after random creation" in {
//        controller.createRandomField(7)
//        observer.updated should be(true)
//        controller.field.possiblePosition(0,0) should be(true)
//      }
//      "notify its Observer after setting a cell" in {
//        controller.set(0,0, Cell(true, Stone("w+")))
//        observer.updated should be(true)
//        controller.field.cell(0,0).content.whichColor should be (Color.white)
//      }

  }
  "new" should {
    val field = new Field(7)
    val controller = new Controller(field)
    "handle undo/redo correctly on an empty undo-stack" in {
      controller.field.cell(0, 0).isSet should be(false)
      controller.undo
      controller.field.cell(0, 0).isSet should be(false)
      controller.redo
      controller.field.cell(0, 0).isSet should be(false)
    }
    "handle undo/redo of setting a cell correctly" in {
      controller.set(0, 0)
      controller.field.cell(0, 0).isSet should be(true)
      controller.field.cell(0, 0).getContent.whichColor should be(Color.black)
      controller.undo
      controller.field.cell(0, 0).isSet should be(false)
      controller.field.cell(0, 0).getContent.whichColor should be(Color.noColor)
      controller.redo
      controller.field.cell(0, 0).isSet should be(true)
      controller.field.cell(0, 0).getContent.whichColor should be(Color.black)
      controller.statusText should be("Redo")
    }
    "should return valid values with its methods" in {
      controller.cell(0, 0).getContent.whichColor should be (Color.black)
      controller.isSet(0, 0) should be (true)
      controller.isSet(0,3) should be (false)
      controller.available(6, 6) should be (true)
      controller.possiblePosition(0, 1) should be(false)
      controller.placedStones() should be (1)
      controller.fieldsize should be (7)
    }
  }
}
