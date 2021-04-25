package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.Controller
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GUISpec extends AnyWordSpec with Matchers {
  "A GUI" when {
    val controller = new Controller
    "created" should {
      val gui = new GUI(controller)
      "have a title" in {
        gui.title should be("Mill")
      }
      /* "be able to update itself" in {
        gui.updateField()
      }*/
    }
  }
}
