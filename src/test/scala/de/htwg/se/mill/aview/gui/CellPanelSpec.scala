package de.htwg.se.mill.aview.gui

import de.htwg.se.mill.controller.controllerComponent.controllerBaseImpl.Controller
import org.scalatest.{Matchers, WordSpec}

class CellPanelSpec extends WordSpec with Matchers {
  "A CellPanel" when {
    val controller = new Controller
    "created" should {
      val winnerCellPanel = new CellPanel(0, 0, controller)
      "should be able to pop up Winners Menu" in {
        winnerCellPanel.winnerDialog()
      }
      val availableCellPanel = new CellPanel(0, 0, controller)
      "have the cellType 'available'" in {
        availableCellPanel.cellType(0, 0) should be (2)
      }
    }
    "placed a white stone within it" should {
      controller.handleClick(0, 3)({ case Some(_) => {} })
      val blackCellPanel = new CellPanel(0, 3, controller)
      "have the cellType 'available'" in {
        blackCellPanel.cellType(0, 3) should be(0)
      }
    }
    "placed a black stone within it" should {
      controller.handleClick(0, 6)({ case Some(_) => {} })
      val whiteCellPanel = new CellPanel(0, 6, controller)
      "have the cellType 'available'" in {
        whiteCellPanel.cellType(0, 6) should be (1)
      }
    }
    "unavailable" should {
      val unavailableCellType = new CellPanel(0, 1, controller)
      "have the cellType 'unavailable'" in {
        unavailableCellType.cellType(0, 1) should be (3)
      }
    }
  }

}
