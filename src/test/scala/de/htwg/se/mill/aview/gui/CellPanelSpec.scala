package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.mill.controller.controllerComponent.controllerStubImpl.ControllerStub
import org.scalatest.{Matchers, WordSpec}

class CellPanelSpec extends WordSpec with Matchers {
  "A CellPanel" when {
    "created" should {
      val controller: ControllerStub = new ControllerStub
      val winnerCellPanel = new CellPanel(0, 0, controller)
      "should be able to pop up Winners Menu" in {
        winnerCellPanel.winnerDialog()
      }
      val availableCellPanel = new CellPanel(0, 0, controller)
      "have the cellType 'available'" in {
        // controller.handleClick(0, 3)({ case Some(_) => {} }) // not needed because is stub
        controller.isSet = false
        controller.possiblePosition = true

        availableCellPanel.cellType(0, 0)
        availableCellPanel.cellType should be(2)
      }
    }
    "placed a white stone within it" should {
      val controller: ControllerStub = new ControllerStub
      // controller.handleClick(0, 3)({ case Some(_) => {} })
      val blackCellPanel = new CellPanel(0, 3, controller)
      "have the cellType 'available'" in {
        controller.isSet = true
        controller.possiblePosition = true
        controller.color = 0

        blackCellPanel.cellType(0, 3)
        blackCellPanel.cellType should be(0)
      }
    }
    "placed a black stone within it" should {
      val controller: ControllerStub = new ControllerStub
      // controller.handleClick(0, 6)({ case Some(_) => {} })
      val whiteCellPanel = new CellPanel(0, 6, controller)
      "have the cellType 'available'" in {
        controller.isSet = true
        controller.possiblePosition = true
        controller.color = 1

        whiteCellPanel.cellType(0, 6)
        whiteCellPanel.cellType should be(1)
      }
    }
    "unavailable" should {
      val controller: ControllerStub = new ControllerStub
      val unavailableCellType = new CellPanel(0, 1, controller)
      "have the cellType 'unavailable'" in {
        controller.isSet = false
        controller.possiblePosition = false

        unavailableCellType.cellType(0, 1)
        unavailableCellType.cellType should be(3)
      }
    }
  }

}
