package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.Controller
import org.scalatest.{Matchers, WordSpec}

class GUIPlayerWindowsSpec extends WordSpec with Matchers {
  "A GUIPlayerWindowsSpec" when {
    val controller = new Controller
    "created" should {
      val guiPlayerWindow = new GUIPlayerWindow(controller)
      "be able to change its player window" in {
        guiPlayerWindow.nextPlayer()
      }
    }
  }

}
