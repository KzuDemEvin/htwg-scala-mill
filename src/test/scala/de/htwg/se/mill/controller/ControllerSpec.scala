package de.htwg.se.mill.controller

import de.htwg.se.mill.model.{Cell, Color, Field, Stone}
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
        override def update: Boolean = {updated = true; updated}
      }
      controller.add(observer)
      "notify its Observer after creation" in {
        controller.createEmptyField(7)
        observer.updated should be(true)
        controller.field.size should be(7)
      }
      "notify its Observer after random creation" in {
        controller.createRandomField(7, 9)
        observer.updated should be(true)
        controller.field.possiblePosition(0,0) should be(true)
      }
      "notify its Observer after setting a cell" in {
        controller.set(0,0, Cell(true, Stone(1, Color.white)))
        observer.updated should be(true)
        controller.field.cell(0,0).content.whichColor should be (Color.white)
      }
    }
  }
}
