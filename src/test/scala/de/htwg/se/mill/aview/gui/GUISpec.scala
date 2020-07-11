package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.mill.model.fieldComponent.fieldAdvancedImpl.Field
import org.scalatest.{Matchers, WordSpec}

class GUISpec extends WordSpec with Matchers {
  "A GUI" when {
    val controller = new Controller(new Field(7))
    "created" should {
      var gui = new GUI(controller)
      "have a title" in {
        gui.title should be("Mill")
      }
      "be able to update itself" in {
        gui.updateField()
      }
    }
  }
}
