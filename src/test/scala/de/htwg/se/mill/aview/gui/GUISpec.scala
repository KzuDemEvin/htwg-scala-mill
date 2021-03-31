package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.mill.model.fieldComponent.fieldAdvancedImpl.Field
import org.scalatest.{Matchers, WordSpec}

class GUISpec extends WordSpec with Matchers {
  "A GUI" when {
    val normalSize = 7
    val controller = new Controller(new Field(normalSize))
    "created" should {
      val gui = new GUI(controller)
      "have a title" in {
        gui.title should be("Mill")
      }
      "be able to update itself" in {
        gui.updateField()
      }
    }
  }
}
