package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.mill.model.fieldComponent.fieldAdvancedImpl.Field
import org.scalatest.{Matchers, WordSpec}

class GUIPlayerWindowsSpec extends WordSpec with Matchers {
  "A GUIPlayerWindowsSpec" when {
    var controller = new Controller(new Field(7))
    "created" should {
      var guiPlayerWindow = new GUIPlayerWindow(controller)
      "be able to change its player window" in {
        guiPlayerWindow.nextPlayer()
      }
    }
  }

}
